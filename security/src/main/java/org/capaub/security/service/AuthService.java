package org.capaub.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public Map<String, String> authenticate(String grantType, String username, String password, boolean withRefreshToken, String refreshToken) {
        String subject;
        String scope;

        if (grantType.equals("password")) {
            Authentication authentication = authenticateWithUsernamePassword(username, password);
            subject = authentication.getName();
            scope = getAuthorities(authentication.getAuthorities());

        } else if (grantType.equals("refresh_token")) {
            if (refreshToken == null) {
                throw new IllegalArgumentException("Refresh Token is required");
            }

            Jwt decodedJWT = decodeJwt(refreshToken);
            subject = decodedJWT.getSubject();
            scope = getAuthorities(loadUserAuthorities(subject));
        } else {
            throw new IllegalArgumentException("Invalid grant type");
        }

        return generateTokens(subject, scope, withRefreshToken);
    }

    private Authentication authenticateWithUsernamePassword(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    private Jwt decodeJwt(String refreshToken) {
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (JwtException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Collection<? extends GrantedAuthority> loadUserAuthorities(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails.getAuthorities();
    }

    private String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

    private Map<String, String> generateTokens(String subject, String scope, boolean withRefreshToken) {
        Map<String, String> tokens = new HashMap<>();
        Instant now = Instant.now();

        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plus(withRefreshToken ? 1 : 5, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();
        tokens.put("access_token", accessToken);

        if (withRefreshToken) {
            JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(now)
                    .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                    .issuer("security-service")
                    .build();
            String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();
            tokens.put("refresh_token", refreshToken);
        }

        return tokens;
    }
}

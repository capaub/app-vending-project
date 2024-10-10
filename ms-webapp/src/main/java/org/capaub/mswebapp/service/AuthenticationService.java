package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.dto.AppUserDTO;
import org.capaub.mswebapp.service.client.CompanyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final CompanyClient companyClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Attempting to load user by email: {}", email);
        AppUserDTO appUserDTO = companyClient.getUserByEmail(email);
        if (appUserDTO == null) {
            logger.error("User {} not found", email);
            throw new UsernameNotFoundException(String.format("User %s not found", email));
        }

        logger.info("User found: {}", appUserDTO.getEmail());

        companyClient.updateConnectedAt(email);

        List<GrantedAuthority> authorities = appUserDTO.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        logger.info("Role found: {}", authorities);

        return User
                .withUsername(appUserDTO.getEmail())
                .password(appUserDTO.getPassword())
                .authorities(authorities)
                .build();
    }
}
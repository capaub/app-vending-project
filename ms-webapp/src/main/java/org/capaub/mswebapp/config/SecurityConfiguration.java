package org.capaub.mswebapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.AuthenticationService;
import org.capaub.mswebapp.service.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationService authenticationService;
    private final SessionService sessionService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers(
                               "/css/global.css",
                               "/js/global.js",
                               "/img/newLogo.svg",
                               "/img/backgroundHeader.jpg",
                               "/signUp",
                               "/signIn").permitAll()
                       .requestMatchers("/admin/**").hasRole("ADMIN")
                       .requestMatchers("/stocker/**").hasRole("STOCKER")
                       .requestMatchers("/user/**").hasRole("USER")
                       .anyRequest().authenticated()
               )
               .formLogin(form -> form
                       .loginPage("/signIn")
                       .permitAll()
                       .usernameParameter("email")
                       .defaultSuccessUrl("/index", true)
                       .successHandler(new AuthenticationSuccessHandler() {
                           @Override
                           public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                               sessionService.sessionUser();
                               response.sendRedirect("/index");
                           }
                       })
               )
               .logout(logout -> logout
                       .logoutUrl("/logout")
                       .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                       .permitAll()
               )
               .build();
   }
}
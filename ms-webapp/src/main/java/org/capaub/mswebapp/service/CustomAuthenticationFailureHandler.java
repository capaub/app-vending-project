package org.capaub.mswebapp.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        if (exception instanceof InternalAuthenticationServiceException &&
                exception.getCause() instanceof PasswordNotSetException) {
            String username = exception.getMessage().split("username=")[1];
            response.sendRedirect("/createPassword?username=" + username);
        } else {
            response.sendRedirect("/signIn?error=true");
        }
    }
}

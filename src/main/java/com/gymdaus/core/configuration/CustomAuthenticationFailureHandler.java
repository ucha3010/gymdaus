package com.gymdaus.core.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof DisabledException) {
            response.sendRedirect("/login-page/?error=user.disabled");
        } else if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            response.sendRedirect("/login-page/?error=invalid.user.or.password");
        } else {
            response.sendRedirect("/login-page/");
        }
    }
}

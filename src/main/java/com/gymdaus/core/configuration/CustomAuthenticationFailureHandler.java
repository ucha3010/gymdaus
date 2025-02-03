package com.gymdaus.core.configuration;

import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
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
        LoggerMapper.log(Level.WARN, Utils.getMethodName(), exception.getMessage(), this.getClass());
    }
}

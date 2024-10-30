package com.gymdaus.core.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Component
public class CustomLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Autowired
    private SessionData sessionData;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String newLocale = request.getParameter(getParamName());
        if (newLocale != null) {
            Locale locale = Locale.forLanguageTag(newLocale);
            sessionData.setSelectedLocale(locale); // Actualiza en la sesión
        }
        return super.preHandle(request, response, handler);
    }
}


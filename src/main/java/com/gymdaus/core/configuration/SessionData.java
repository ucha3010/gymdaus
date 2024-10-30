package com.gymdaus.core.configuration;

import com.gymdaus.core.model.GymModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class SessionData {

    private GymModel gymModel;
    private Locale selectedLocale = Locale.getDefault();

}
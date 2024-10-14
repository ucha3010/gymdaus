package com.gymdaus.core.configuration;

import com.gymdaus.core.model.GymModel;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private GymModel gymModel;

    public GymModel getGimnasioModel() {
        return gymModel;
    }

    public void setGimnasioModel(GymModel gymModel) {
        this.gymModel = gymModel;
    }
}

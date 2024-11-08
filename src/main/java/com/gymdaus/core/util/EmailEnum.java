package com.gymdaus.core.util;

public enum EmailEnum {
    EMPTY("", "", ""),
    GMAIL("Gmail", "smtp.gmail.com", "587"),
    OFFICE365("Office 365", "smtp.office365.com", "587"),
    YAHOO("Yahoo", "smtp.mail.yahoo.com", "587");

    private final String provider;
    private final String host;
    private final String port;

    EmailEnum(String provider, String host, String port) {
        this.provider = provider;
        this.host = host;
        this.port = port;
    }

    public String getProvider() {
        return provider;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}

package com.gymdaus.core.util;

public enum EmailEnum {
    VACIO("Seleccionar proveedor","",""),
    GMAIL("Gmail","smtp.gmail.com","587"),
    OFFICE365("Office 365","smtp.office365.com","587"),
    YAHOO("Yahoo","smtp.mail.yahoo.com","587");

    private final String proveedor;
    private final String host;
    private final String port;

    EmailEnum(String proveedor, String host, String port) {
        this.proveedor = proveedor;
        this.host = host;
        this.port = port;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}

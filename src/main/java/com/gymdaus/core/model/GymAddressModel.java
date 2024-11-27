package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymAddressModel {

    private Long id;
    private String name;
    private boolean enabled;
    private String email;
    private String emailHost;
    private String emailPassword;
    private String emailPort;
    private String phone;
    private String addressStreet;
    private String addressNumber;
    private String addressOther;
    private String addressCity;
    private String addressZip;
    private CountryModel countryModel;
    private GymModel gymModel;

    @Override
    public String toString() {
        return "GymAddressModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", email='" + email + '\'' +
                ", emailHost='" + emailHost + '\'' +
                ", emailPort='" + emailPort + '\'' +
                ", phone='" + phone + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressOther='" + addressOther + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressZip='" + addressZip + '\'' +
                ", countryModel=" + countryModel +
                ", gymModel=" + gymModel +
                '}';
    }
}
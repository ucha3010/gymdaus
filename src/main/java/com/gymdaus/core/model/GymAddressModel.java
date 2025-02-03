package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymAddressModel {

    private Long id;
    private String name;
    private boolean enabled;
    private String email;
    private String phone;
    private String addressStreet;
    private String addressNumber;
    private String addressOther;
    private String addressCity;
    private String addressZip;
    private CountryModel countryModel;
    private boolean sepaDirectDebitAvailable;
    private GymModel gymModel;
}
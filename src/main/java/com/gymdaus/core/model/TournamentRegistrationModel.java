package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TournamentRegistrationModel {

    private int id;
    private boolean registrationAdult;
    private boolean registrationYoung;
    private boolean registrationInclusive;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;
    private String tournamentDate;
    private String tournamentName;
    private String tournamentAddress;
    private String category;
    private int idGym;
    private int idTournament;

    //Registered user
    private String registeredName;
    private String registered1Lastname;
    private String registered2Lastname;
    private String registeredIdCard;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registeredBirthdate;
    private String registeredSex;
    private String registeredAddressStreet;
    private String registeredAddressNumber;
    private String registeredAddressOther;
    private String registeredAddressCity;
    private String registeredAddressPostcode;
    private String gym;
    private String country;
    private String belt;
    private String poomsae;

    //Authorizer user
    private String authorizerName;
    private String authorizer1Lastname;
    private String authorizer2Lastname;
    private String authorizerIdCard;
    private String relationship;
    private String authorizerAddressStreet;
    private String authorizerAddressNumber;
    private String authorizerAddressOther;
    private String authorizerAddressCity;
    private String authorizerAddressPostcode;

    //Payment data
    private boolean paid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;
    private String notes;
}

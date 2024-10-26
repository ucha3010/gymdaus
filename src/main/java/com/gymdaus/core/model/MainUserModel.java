package com.gymdaus.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MainUserModel {

    private Long id;
    private Date registrationDate;
    private String tournamentName;
    private Date tournamentDate;
    private String name;
    private String lastname;
    private String secondLastname;
    private boolean ownRegistration;
}

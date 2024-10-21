package com.gymdaus.core.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenModel {

    private String id;
    private String username;
    private Date expiration;
    private int attempts;
    private Long gymId;
    private String password;
    private String name;
    private String lastname;
    private String secondLastname;

}

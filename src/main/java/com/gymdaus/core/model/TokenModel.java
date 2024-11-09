package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenModel {

    private String id;
    private int attempts;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiration;
    private String password;
    private GymModel gymModel;
    private String username;
    private String methodToBeUse;
    private String usernameSendChange;

}

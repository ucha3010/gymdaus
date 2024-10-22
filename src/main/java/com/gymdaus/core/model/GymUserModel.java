package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymUserModel {

    private int id;
    private String registrationUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;
    private UserModel userModel;
    private GymModel gymModel;

}

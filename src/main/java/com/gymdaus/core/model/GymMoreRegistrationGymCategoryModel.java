package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationGymCategoryModel {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date registrationDate;
    private String registrationUser;
    private GymMoreRegistrationModel gymMoreRegistrationModel;
    private GymCategoryModel gymCategoryModel;

}

package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymCategoryGymBeltModel {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;
    private String registrationUser;
    private GymCategoryModel gymCategoryModel;
    private GymBeltModel gymBeltModel;

}
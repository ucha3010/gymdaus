package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymCategoryModel {

    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startAge;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endAge;
    private AgeCategoryModel ageCategoryModel;
    private GymMoreRegistrationBeltModel startBeltModel;
    private GymMoreRegistrationBeltModel endBeltModel;
    private GymPoomsaeModel gymPoomsaeModel;
    private int position;
    private GymMoreRegistrationModel gymMoreRegistrationModel;

}
package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationBeltModel {

    private Long id;
    private String colour;
    private int position;
    private GymMoreRegistrationModel gymMoreRegistrationModel;

}
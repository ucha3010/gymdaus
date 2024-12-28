package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationGymBeltModel {

    private GymMoreRegistrationModel gymMoreRegistrationModel;
    private GymBeltModel gymBeltModel;
    private int position;

}
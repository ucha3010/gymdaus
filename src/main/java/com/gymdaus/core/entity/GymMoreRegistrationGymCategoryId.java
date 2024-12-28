package com.gymdaus.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymMoreRegistrationGymCategoryId implements Serializable {

    private Long gymMoreRegistrationId;
    private Long gymCategoryId;

}
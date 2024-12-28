package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(GymMoreRegistrationGymBeltId.class)
@Table(name = "gym_more_registration_gym_belt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationGymBelt {

    @Id
    private Long gymMoreRegistrationId;
    @Id
    private Long gymBeltId;
    private int position;

}
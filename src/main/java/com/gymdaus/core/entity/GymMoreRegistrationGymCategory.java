package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@IdClass(GymMoreRegistrationGymCategoryId.class)
@Table(name = "gym_more_registration_gym_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationGymCategory {

    @Id
    private Long gymMoreRegistrationId;
    @Id
    private Long gymCategoryId;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false)
    private String registrationUser;

}
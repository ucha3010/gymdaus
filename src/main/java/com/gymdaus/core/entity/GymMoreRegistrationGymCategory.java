package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_more_registration_gym_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationGymCategory {

    @Id
    @SequenceGenerator(name = "gymMoreRegistrationGymCategoryGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymMoreRegistrationGymCategoryGenerator")
    private Long id;
    @Column(nullable = false)
    private Long gymMoreRegistrationId;
    @Column(nullable = false)
    private Long gymCategoryId;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false)
    private String registrationUser;

}
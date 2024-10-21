package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gym_more_registration_belt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationBelt {

    @Id
    @SequenceGenerator(name = "gymMoreRegistrationBeltGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymMoreRegistrationBeltGenerator")
    private Long id;
    private Long gymMoreRegistrationId;
    private Long beltId;
    private int position;

}
package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_more_registration_participating_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationParticipatingEntity {

    @Id
    @SequenceGenerator(name = "gymMoreRegistrationPEGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymMoreRegistrationPEGenerator")
    private Long id;
    private Long gymMoreRegistrationId;
    private Long participatingEntityId;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false)
    private Date registrationUser;

}
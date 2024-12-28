package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@IdClass(GymMoreRegistrationParticipatingEntityId.class)
@Table(name = "gym_more_registration_participating_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationParticipatingEntity {

    @Id
    private Long gymMoreRegistrationId;
    @Id
    private Long participatingEntityId;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String registrationUser;
    private int position;

}
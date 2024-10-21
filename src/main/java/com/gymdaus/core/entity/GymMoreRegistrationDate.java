package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_more_registration_date")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationDate {

    @Id
    @SequenceGenerator(name = "gymMoreRegistrationDateGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymMoreRegistrationDateGenerator")
    private Long id;
    private Long gymMoreRegistrationId;
    @Column(nullable = false)
    private Date startDateTime;
    @Column(nullable = false)
    private Date endDateTime;

}
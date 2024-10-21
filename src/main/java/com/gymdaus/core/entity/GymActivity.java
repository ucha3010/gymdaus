package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymActivity {

    @Id
    @SequenceGenerator(name = "gymActivityGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymActivityGenerator")
    private Long id;
    private Long activityId;
    private Long gymId;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false)
    private Date registrationUser;

}
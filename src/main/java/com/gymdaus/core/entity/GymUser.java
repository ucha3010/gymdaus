package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymUser {

    @Id
    @SequenceGenerator(name = "gymUserGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymUserGenerator")
    private Long id;
    private Long gymId;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String username;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false)
    private Date registrationUser;

}
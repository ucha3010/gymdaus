package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_category_gym_belt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymCategoryGymBelt {

    @Id
    @SequenceGenerator(name = "gymCategoryGymBeltGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymCategoryGymBeltGenerator")
    private Long id;
    private Long gymCategoryId;
    private Long gymBeltId;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String registrationUser;

}
package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymCategory {

    @Id
    @SequenceGenerator(name = "gymCategoryGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymCategoryGenerator")
    private Long id;
    private int gymMoreRegistrationId;
    @Column(nullable = false, length = Constants.GYM_CATEGORY_NAME)
    private String name;
    private Date startAge;
    private Date endAge;
    private Long ageCategoryId;
    private Long startBeltId;
    private Long endBeltId;
    private Long poomsaeId;
    private int position;

}
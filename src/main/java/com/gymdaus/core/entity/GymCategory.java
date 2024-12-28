package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(nullable = false, length = Constants.GYM_CATEGORY_NAME)
    private String name;
    private int startAge;
    private int endAge;
    private Long gymId;
    private Long poomsaeId;
    private int position;

}
package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "age_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgeCategory {

    @Id
    @SequenceGenerator(name = "ageCategoryGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ageCategoryGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.AGE_CATEGORY_NAME)
    private String name;
    private Date startAge;
    private Date endAge;
    private Long gymMoreRegistrationId;
    private int position;

}
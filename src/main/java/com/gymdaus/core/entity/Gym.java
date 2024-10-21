package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gym {

    @Id
    @SequenceGenerator(name = "gymGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.GYM_NAME)
    private String name;
    private boolean enabled;
    @Column(nullable = false, length = Constants.TAX_ID_CODE)
    private String taxIdCode;
    @Column(nullable = false)
    private Date registrationDate;
    private Date modificationDate;
    @Column(length = Constants.USERNAME_OR_ID_CARD)
    private String modificationUsername;
    private int contractedRecords;
    private int contractedVisibility;

}
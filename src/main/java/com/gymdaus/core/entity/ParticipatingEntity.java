package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participating_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParticipatingEntity {

    @Id
    @SequenceGenerator(name = "participatingEntityGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participatingEntityGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.GYM_NAME)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String notes;
    private Long gymId;
    private int position;

}
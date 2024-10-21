package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gym_belt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymBelt {

    @Id
    @SequenceGenerator(name = "gymBeltGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymBeltGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.BELT_COLOUR)
    private String colour;
    private Long gymId;
    private int position;

}
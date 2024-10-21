package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gym_poomsae")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymPoomsae {

    @Id
    @SequenceGenerator(name = "gymPoomsaeGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymPoomsaeGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.POOMSAE_NAME)
    private String name;
    private Long gymId;
    private int position;

}
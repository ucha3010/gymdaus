package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "more_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoreRegistration {

    @Id
    @SequenceGenerator(name = "moreRegistrationGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moreRegistrationGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.MORE_REGISTRATION_NAME)
    private String name;
    private boolean enabled;

}
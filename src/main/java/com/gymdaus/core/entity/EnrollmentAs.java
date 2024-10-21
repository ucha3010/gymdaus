package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enrollment_as")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrollmentAs {

    @Id
    @SequenceGenerator(name = "enrollmentAsGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollmentAsGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.ENROLLMENT_AS_NAME)
    private String name;
    private int position;

}
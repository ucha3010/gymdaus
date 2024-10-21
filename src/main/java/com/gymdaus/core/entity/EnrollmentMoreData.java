package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "enrollment_more_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrollmentMoreData {

    @Id
    private Long enrollmentId;
    @Column(length = Constants.CATEGORY_NAME)
    private String category;
    @Column(length = Constants.BELT_NAME)
    private String belt;
    @Column(length = Constants.POOMSAE_NAME)
    private String poomsae;
    private boolean whatsappAuthorization;
    @Column(columnDefinition = "TEXT")
    private String notes;

}
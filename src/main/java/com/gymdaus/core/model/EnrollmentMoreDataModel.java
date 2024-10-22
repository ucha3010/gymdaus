package com.gymdaus.core.model;

import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrollmentMoreDataModel {

    @Id
    private Long enrollmentId;
    private String category;
    private String belt;
    private String poomsae;
    private boolean whatsappAuthorization;
    private String notes;
    private EnrollmentModel enrollmentModel;

}
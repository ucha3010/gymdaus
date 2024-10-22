package com.gymdaus.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignatureModel {

    private Long id;
    private Long operationId; //(id de la operación que sea como una inscripción, una subida de documento, etc)
    private String operationName;
    private int attempts;
    private GymModel gym;
    private boolean signed;
    private EnrollmentModel enrollmentModel;
    private GymDocumentManagerModel gymDocumentManagerModel;

}
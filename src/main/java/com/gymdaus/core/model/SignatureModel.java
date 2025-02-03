package com.gymdaus.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignatureModel {

    private Long id;
    private Long operationId; //(id de la operación que sea como una inscripción, una subida de documento, etc)
    private String operationName;
    private String username;
    private GymModel gymModel;
    private boolean signed;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;
    private String code;
    private int attempts;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;
    private int sentCodeAttempts; //max Constants.MAXIMUM_SENT_CODE_ATTEMPTS
    private boolean signatureLocked;
    private String tableToSearch;
    private String language;

    public SignatureModel(Long operationId, String operationName) {
        this.operationId = operationId;
        this.operationName = operationName;
    }

    public SignatureModel(Long operationId, String operationName, String username, GymModel gymModel, String tableToSearch, String language) {
        this.operationId = operationId;
        this.operationName = operationName;
        this.username = username;
        this.gymModel = gymModel;
        this.tableToSearch = tableToSearch;
        this.language = language;
    }
}
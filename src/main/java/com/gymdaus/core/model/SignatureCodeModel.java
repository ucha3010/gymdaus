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
public class SignatureCodeModel {

    private Long id;
    private String code;
    private String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;
    private Long operationId;
    private String operationName;
    private String signedOkPage;
    private GymModel gym;

}

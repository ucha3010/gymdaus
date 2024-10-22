package com.gymdaus.core.model;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrollmentModel {

    private Long id;
    private boolean own;
    private boolean minor;
    private boolean inclusive;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrollmentDate;
    private String name; //del torneo, licencia o lo que sea
    private String gymName;
    private String addressStreet;
    private String addressNumber;
    private String addressOther;
    private String addressCity;
    private String addressZip;
    private String addressCountry;
    private String userEnrollmentName;
    private String userEnrollmentLastname;
    private String userEnrollmentSecondLastname;
    private String userEnrollmentIdCard;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userEnrollmentBirthdate;
    private String userEnrollmentSex;
    private String authorizerEnrollmentAddressStreet;
    private String authorizerEnrollmentAddressNumber;
    private String authorizerEnrollmentAddressOther;
    private String authorizerEnrollmentAddressCity;
    private String authorizerEnrollmentAddressZip;
    private String authorizerEnrollmentAddressCountry;
    private String authorizerEnrollmentName;
    private String authorizerEnrollmentLastname;
    private String authorizerEnrollmentSecondLastname;
    private String authorizerEnrollmentIdCard;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date authorizerEnrollmentBirthdate;
    private String authorizerEnrollmentAs;
    private String email;
    private String phone;
    private boolean paid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paidDate;
    private boolean sepaDirectDebit;
    private String sepaAccountNumber;
    private String sepaAccountPerson;
    private String swift;
    private boolean signed;
    private GymActivityModel gymActivityModel;
    private GymMoreRegistrationModel gymMoreRegistrationModel;
    private UserModel userModel;
    private EnrollmentAsModel enrollmentAsModel;
    private EnrollmentMoreDataModel enrollmentMoreDataModel;
    private SignatureModel signatureModel;

}
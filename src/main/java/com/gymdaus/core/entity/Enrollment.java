package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean own;
    private boolean minor;
    private boolean inclusive;
    @Column(nullable = false)
    private Date enrollmentDate;
    private Long gymActivityId;
    private Long gymMoreRegistrationId;
    @Column(nullable = false, length = Constants.ENROLLMENT_NAME)
    private String name; //del torneo, licencia o lo que sea
    @Column(length = Constants.GYM_NAME)
    private String gymName;
    @Column(length = Constants.ADDRESS_STREET)
    private String addressStreet;
    @Column(length = Constants.ADDRESS_NUMBER)
    private String addressNumber;
    @Column(length = Constants.ADDRESS_OTHER)
    private String addressOther;
    @Column(length = Constants.ADDRESS_CITY)
    private String addressCity;
    @Column(length = Constants.ADDRESS_ZIP)
    private String addressZip;
    @Column(length = Constants.COUNTRY_NAME)
    private String addressCountry;
    @Column(length = Constants.NAME)
    private String userEnrollmentName;
    @Column(length = Constants.LASTNAME)
    private String userEnrollmentLastname;
    @Column(length = Constants.SECOND_LASTNAME)
    private String userEnrollmentSecondLastname;
    @Column(length = Constants.USERNAME_OR_ID_CARD)
    private String userEnrollmentIdCard;
    private Date userEnrollmentBirthdate;
    @Column(length = Constants.SEX)
    private String userEnrollmentSex;
    @Column(length = Constants.ADDRESS_STREET)
    private String authorizerEnrollmentAddressStreet;
    @Column(length = Constants.ADDRESS_NUMBER)
    private String authorizerEnrollmentAddressNumber;
    @Column(length = Constants.ADDRESS_OTHER)
    private String authorizerEnrollmentAddressOther;
    @Column(length = Constants.ADDRESS_CITY)
    private String authorizerEnrollmentAddressCity;
    @Column(length = Constants.ADDRESS_ZIP)
    private String authorizerEnrollmentAddressZip;
    @Column(length = Constants.COUNTRY_NAME)
    private String authorizerEnrollmentAddressCountry;
    @Column(length = Constants.NAME)
    private String authorizerEnrollmentName;
    @Column(length = Constants.LASTNAME)
    private String authorizerEnrollmentLastname;
    @Column(length = Constants.SECOND_LASTNAME)
    private String authorizerEnrollmentSecondLastname;
    @Column(length = Constants.USERNAME_OR_ID_CARD)
    private String authorizerEnrollmentIdCard;
    private Date authorizerEnrollmentBirthdate;
    @Column(length = Constants.ENROLLMENT_AS_NAME)
    private String authorizerEnrollmentAs;
    @Column(length = Constants.EMAIL)
    private String email;
    @Column(length = Constants.PHONE)
    private String phone;
    private boolean paid;
    private Date paidDate;
    private boolean sepaDirectDebit;
    @Column(length = Constants.SEPA_ACCOUNT_NUMBER)
    private String sepaAccountNumber;
    @Column(length = Constants.SEPA_ACCOUNT_PERSON)
    private String sepaAccountPerson;
    @Column(length = Constants.SWIFT)
    private String swift;
    private boolean signed;

}
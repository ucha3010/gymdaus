package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationModel {

    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;
    private String registrationUser;
    private boolean enabled;
    private String addressStreet;
    private String addressNumber;
    private String addressOther;
    private String addressCity;
    private String addressZip;
    private CountryModel countryModel;
    private boolean needsPaid;
    private boolean sepa;
    private GymModel gymModel;
    private List<EnrollmentModel> enrollmentModelList;
    private MoreRegistrationModel moreRegistrationModel;
    private List<GymMoreRegistrationBeltModel> gymMoreRegistrationBeltModelList;
    private List<GymMoreRegistrationDateModel> gymMoreRegistrationDateModelList;
    private List<AgeCategoryModel> ageCategoryModelList;
    private List<GymCategoryModel> gymCategoryModelList;
    private List<GymMoreRegistrationParticipatingEntityModel> gymMoreRegistrationParticipatingEntityModelList;

}
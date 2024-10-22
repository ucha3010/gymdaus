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
public class GymModel {

    private Long id;
    private String name;
    private boolean enabled;
    private String taxId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modificationDate;
    private String modificationUsername;
    private int contractedRecords;
    private int contractedVisibility;
    private List<GymParameterModel> gymParameterModelList;
    private List<GymAddressModel> gymAddressModelList;
    private List<GymPhotoModel> gymPhotoModelList;
    private List<GymActivityModel> gymActivityModelList;
    private List<GymMoreRegistrationModel> gymMoreRegistrationModelList;
    private List<GymPoomsaeModel> gymPoomsaeModelList;
    private List<GymBeltModel> gymBeltModelList;
    private List<GymUserModel> gymUserModelList;
    private List<GymDocumentManagerModel> gymDocumentManagerModelList;
    private List<ParticipatingEntityModel> participatingEntityModelList;
}

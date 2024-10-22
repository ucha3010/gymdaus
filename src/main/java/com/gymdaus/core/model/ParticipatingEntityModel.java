package com.gymdaus.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParticipatingEntityModel {

    private Long id;
    private String name;
    private String notes;
    private int position;
    private GymModel gymModel;
    private List<GymMoreRegistrationParticipatingEntityModel> gymMoreRegistrationParticipatingEntityModelList;

}
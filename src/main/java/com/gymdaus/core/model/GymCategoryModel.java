package com.gymdaus.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymCategoryModel {

    private Long id;
    private String name;
    private int startAge;
    private int endAge;
    private GymModel gymModel;
    private GymPoomsaeModel gymPoomsaeModel;
    private int position;
    private List<GymBeltModel> gymBeltModelList;
    private List<Long> beltIdList;
    private String beltNameList;
    private boolean selected;

}
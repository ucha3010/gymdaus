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
public class AgeCategoryModel {

    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startAge;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endAge;
    private int position;
    private List<GymMoreRegistrationModel> gymMoreRegistrationModelList;

}
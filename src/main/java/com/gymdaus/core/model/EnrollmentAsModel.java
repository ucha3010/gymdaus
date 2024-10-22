package com.gymdaus.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrollmentAsModel {

    private Long id;
    private String name;
    private int position;
    private List<EnrollmentAsModel> enrollmentAsModelList;

}
package com.gymdaus.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityModel {

    private Long id;
    private String name;
    private boolean enabled;
    private List<GymActivityModel> gymActivityModelList;

}
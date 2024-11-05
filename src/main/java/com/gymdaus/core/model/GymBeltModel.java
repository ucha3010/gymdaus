package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymBeltModel {

    private Long id;
    private String color;
    private int position;
    private GymModel gymModel;

}
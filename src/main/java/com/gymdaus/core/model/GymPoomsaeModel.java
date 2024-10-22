package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymPoomsaeModel {

    private Long id;
    private String name;
    private int position;
    private GymModel gymModel;

}
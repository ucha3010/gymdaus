package com.gymdaus.core.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParameterId implements Serializable {

    private String keyData;
    private Long gymId;

}
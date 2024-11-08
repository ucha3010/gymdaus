package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UtilModel {

    private String key;
    private String value;
    private int gymCode;

    public UtilModel(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

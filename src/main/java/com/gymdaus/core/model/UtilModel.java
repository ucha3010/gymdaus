package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UtilModel {

    private String key;
    private String valuer;
    private int gymCode;

    public UtilModel(String key, String valuer) {
        this.key = key;
        this.valuer = valuer;
    }
}

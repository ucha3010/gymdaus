package com.gymdaus.core.model;

import com.gymdaus.core.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymParameterModel {

    private String keyData;
    private String value;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modificationDate;
    private String modificationUsername;
    private GymModel gymModel;

    @Override
    public String toString() {
        return "GymParameterModel{" +
                "keyData='" + keyData +
                ", value='" + Utils.ofuscar(value) +
                ", modificationDate=" + modificationDate +
                ", modificationUsername=" + modificationUsername +
                '}';
    }

}
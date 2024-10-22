package com.gymdaus.core.model;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoreRegistrationModel {

    private Long id;
    private String name;
    private boolean enabled;
    private List<GymMoreRegistrationModel> gymMoreRegistrationModelList;

}
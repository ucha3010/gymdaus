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
public class GymActivityModel {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;
    private String registrationUser;
    private boolean enabled;
    private ActivityModel activityModel;
    private GymModel gymModel;
    private GymAddressModel gymAddressModel;
    List<GymActivityScheduleModel> gymActivityScheduleModelList;
    private List<EnrollmentModel> enrollmentModelList;

}
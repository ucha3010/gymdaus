package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymActivityScheduleModel {

    private Long id;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private String lastDayOfWeek;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date specificStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date specificEndDate;
    //OffsetTime time = OffsetTime.of(15, 30, 0, 0, ZoneOffset.ofHours(-5)); // 15:30 con zona horaria UTC-5
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String name;
    private String roomName;
    private boolean adult;
    private boolean minor;
    private boolean inclusive;
    private Double price;
    private String currency;
    private int capacity;
    private GymActivityModel gymActivityModel;
    private GymAddressModel gymAddressModel;
    private ActivityModel activityModel;
    private int position;
    private boolean enabled;
    private boolean federationForm;
    private boolean whatsappForm;
    private String description;

}
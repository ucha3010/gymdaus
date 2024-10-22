package com.gymdaus.core.model;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetTime;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date specificStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date specificEndDate;
    //OffsetTime time = OffsetTime.of(15, 30, 0, 0, ZoneOffset.ofHours(-5)); // 15:30 con zona horaria UTC-5
    @DateTimeFormat(pattern = "HH:mm")
    private OffsetTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private OffsetTime endTime;
    private String name;
    private String roomName;
    private GymActivityModel gymActivityModel;

}
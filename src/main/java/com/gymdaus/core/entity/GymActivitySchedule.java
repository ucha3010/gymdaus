package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetTime;
import java.util.Date;


@Entity
@Table(name = "gym_activity_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymActivitySchedule {

    @Id
    @SequenceGenerator(name = "gymScheduleGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymScheduleGenerator")
    private Long id;
    private Long gymActivityId;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private Date specificStartDate;
    private Date specificEndDate;
    //OffsetTime time = OffsetTime.of(15, 30, 0, 0, ZoneOffset.ofHours(-5)); // 15:30 con zona horaria UTC-5
    private OffsetTime startTime;
    private OffsetTime endTime;
    @Column(length = Constants.GYM_ACTIVITY_SCHEDULE_NAME)
    private String name;
    @Column(length = Constants.GYM_ACTIVITY_SCHEDULE_ROOM_NAME)
    private String roomName;

}
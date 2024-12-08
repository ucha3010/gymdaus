package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymActivitySchedule;
import com.gymdaus.core.model.ActivityModel;
import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.model.GymActivityScheduleModel;
import com.gymdaus.core.service.GymAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class MapperGymActivitySchedule {
    @Autowired
    private GymAddressService gymAddressService;

    public GymActivityScheduleModel entity2Model(GymActivitySchedule externObject) {
        GymActivityScheduleModel localObject = new GymActivityScheduleModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getGymActivityId() != 0) {
                GymActivityModel gymActivityModel = new GymActivityModel();
                gymActivityModel.setId(externObject.getGymActivityId());
                localObject.setGymActivityModel(gymActivityModel);
            }
            if (externObject.getActivityId() != 0) {
                ActivityModel activityModel = new ActivityModel();
                activityModel.setId(externObject.getActivityId());
                localObject.setActivityModel(activityModel);
            }
            if (externObject.getGymAddressId() != 0) {
                localObject.setGymAddressModel(gymAddressService.findById(externObject.getGymAddressId()));
            }
            localObject.setMonday(externObject.isMonday());
            localObject.setTuesday(externObject.isTuesday());
            localObject.setWednesday(externObject.isWednesday());
            localObject.setThursday(externObject.isThursday());
            localObject.setFriday(externObject.isFriday());
            localObject.setSaturday(externObject.isSaturday());
            localObject.setSunday(externObject.isSunday());
            localObject.setSpecificStartDate(externObject.getSpecificStartDate());
            localObject.setSpecificEndDate(externObject.getSpecificEndDate());
            localObject.setStartTime(externObject.getStartTime().toLocalTime());
            localObject.setEndTime(externObject.getEndTime().toLocalTime());
            localObject.setName(externObject.getName());
            localObject.setRoomName(externObject.getRoomName());
            localObject.setAdult(externObject.isAdult());
            localObject.setMinor(externObject.isMinor());
            localObject.setInclusive(externObject.isInclusive());
            localObject.setPrice(externObject.getPrice());
            localObject.setCapacity(externObject.getCapacity());
            localObject.setPosition(externObject.getPosition());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }

    public GymActivitySchedule model2Entity(GymActivityScheduleModel externObject) {
        GymActivitySchedule localObject = new GymActivitySchedule();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getGymActivityModel() != null) {
                localObject.setGymActivityId(externObject.getGymActivityModel().getId());
            } else {
                localObject.setGymActivityId(0L);
            }
            if (externObject.getActivityModel() != null) {
                localObject.setActivityId(externObject.getActivityModel().getId());
            } else {
                localObject.setActivityId(0L);
            }
            if (externObject.getGymAddressModel() != null) {
                localObject.setGymAddressId(externObject.getGymAddressModel().getId());
            } else {
                localObject.setGymAddressId(0L);
            }
            localObject.setMonday(externObject.isMonday());
            localObject.setTuesday(externObject.isTuesday());
            localObject.setWednesday(externObject.isWednesday());
            localObject.setThursday(externObject.isThursday());
            localObject.setFriday(externObject.isFriday());
            localObject.setSaturday(externObject.isSaturday());
            localObject.setSunday(externObject.isSunday());
            localObject.setSpecificStartDate(externObject.getSpecificStartDate());
            localObject.setSpecificEndDate(externObject.getSpecificEndDate());
            localObject.setStartTime(externObject.getStartTime().atOffset(ZoneOffset.UTC));
            localObject.setEndTime(externObject.getEndTime().atOffset(ZoneOffset.UTC));
            localObject.setName(externObject.getName());
            localObject.setRoomName(externObject.getRoomName());
            localObject.setAdult(externObject.isAdult());
            localObject.setMinor(externObject.isMinor());
            localObject.setInclusive(externObject.isInclusive());
            localObject.setPrice(externObject.getPrice());
            localObject.setCapacity(externObject.getCapacity());
            localObject.setPosition(externObject.getPosition());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }
}

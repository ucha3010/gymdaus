package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymActivitySchedule;
import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.model.GymActivityScheduleModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymActivitySchedule {

    public GymActivityScheduleModel entity2Model(GymActivitySchedule externObject) {
        GymActivityScheduleModel localObject = new GymActivityScheduleModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getGymActivityId() != 0) {
                GymActivityModel gymActivityModel = new GymActivityModel();
                gymActivityModel.setId(externObject.getGymActivityId());
                localObject.setGymActivityModel(gymActivityModel);
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
            localObject.setStartTime(externObject.getStartTime());
            localObject.setEndTime(externObject.getEndTime());
            localObject.setName(externObject.getName());
            localObject.setRoomName(externObject.getRoomName());
            localObject.setPosition(externObject.getPosition());
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
            localObject.setMonday(externObject.isMonday());
            localObject.setTuesday(externObject.isTuesday());
            localObject.setWednesday(externObject.isWednesday());
            localObject.setThursday(externObject.isThursday());
            localObject.setFriday(externObject.isFriday());
            localObject.setSaturday(externObject.isSaturday());
            localObject.setSunday(externObject.isSunday());
            localObject.setSpecificStartDate(externObject.getSpecificStartDate());
            localObject.setSpecificEndDate(externObject.getSpecificEndDate());
            localObject.setStartTime(externObject.getStartTime());
            localObject.setEndTime(externObject.getEndTime());
            localObject.setName(externObject.getName());
            localObject.setRoomName(externObject.getRoomName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
}

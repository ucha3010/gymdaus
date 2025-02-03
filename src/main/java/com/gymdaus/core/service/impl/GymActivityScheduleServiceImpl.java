package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymActivitySchedule;
import com.gymdaus.core.mapper.MapperGymActivitySchedule;
import com.gymdaus.core.model.GymActivityScheduleModel;
import com.gymdaus.core.repository.GymActivityScheduleRepository;
import com.gymdaus.core.service.GymActivityScheduleService;
import com.gymdaus.core.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service()
public class GymActivityScheduleServiceImpl implements GymActivityScheduleService {

    @Autowired
    private GymActivityScheduleRepository gymActivityScheduleRepository;

    @Autowired
    private MapperGymActivitySchedule mapperGymActivitySchedule;

    @Override
    public List<GymActivityScheduleModel> findAll() {
        List<GymActivityScheduleModel> gymActivityScheduleModelList = new ArrayList<>();
        for (GymActivitySchedule gymActivitySchedule : gymActivityScheduleRepository.findAllByEnabledTrueOrderByPositionAsc()) {
            gymActivityScheduleModelList.add(mapperGymActivitySchedule.entity2Model(gymActivitySchedule));
        }
        return gymActivityScheduleModelList;
    }

    @Override
    public List<GymActivityScheduleModel> findAllByGymAddressIdAndActivityId(Long gymAddressId, Long activityId) {
        List<GymActivityScheduleModel> gymActivityScheduleModelList = new ArrayList<>();
        for (GymActivitySchedule gymActivitySchedule : gymActivityScheduleRepository.findAllByGymAddressIdAndActivityIdAndEnabledTrueOrderByPositionAsc(gymAddressId, activityId)) {
            gymActivityScheduleModelList.add(mapperGymActivitySchedule.entity2Model(gymActivitySchedule));
        }
        return gymActivityScheduleModelList;
    }

    @Override
    public GymActivityScheduleModel findById(Long id) {
        try {
            return mapperGymActivitySchedule.entity2Model(gymActivityScheduleRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymActivityScheduleModel();
        }
    }

    @Override
    public void add(GymActivityScheduleModel gymActivityScheduleModel) {
        gymActivityScheduleModel.setEnabled(Boolean.TRUE);
        gymActivityScheduleRepository.save(mapperGymActivitySchedule.model2Entity(gymActivityScheduleModel));
    }

    @Override
    public void update(GymActivityScheduleModel gymActivityScheduleModel) {
        gymActivityScheduleRepository.save(mapperGymActivitySchedule.model2Entity(gymActivityScheduleModel));
    }

    @Override
    public void delete(Long id) {
        GymActivityScheduleModel gymActivityScheduleModel = findById(id);
        gymActivityScheduleModel.setEnabled(Boolean.FALSE);
        gymActivityScheduleModel.setPosition(gymActivityScheduleRepository.countByGymAddressIdAndActivityIdOrderByPositionAsc(
                gymActivityScheduleModel.getGymAddressModel().getId(), gymActivityScheduleModel.getActivityModel().getId()).intValue());
        update(gymActivityScheduleModel);
        List<GymActivitySchedule> gymActivityScheduleList = gymActivityScheduleRepository.findAllByGymAddressIdAndActivityIdOrderByPositionAsc(
                gymActivityScheduleModel.getGymAddressModel().getId(), gymActivityScheduleModel.getActivityModel().getId());
        for (int i = 0; i < gymActivityScheduleList.size(); i++) {
            if (gymActivityScheduleList.get(i).getPosition() != i) {
                gymActivityScheduleList.get(i).setPosition(i);
                gymActivityScheduleRepository.save(gymActivityScheduleList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(Long gymAddressId, Long activityId, int initialPosition, int finalPosition) {
        GymActivitySchedule gymActivitySchedule = gymActivityScheduleRepository.findByGymAddressIdAndActivityIdAndPosition(gymAddressId, activityId, initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(gymAddressId, activityId, i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(gymAddressId, activityId, i, false);
            }
        }
        gymActivitySchedule.setPosition(finalPosition);
        gymActivityScheduleRepository.save(gymActivitySchedule);
    }

    @Override
    public int findMaxPosition(Long gymAddressId, Long activityId) {
        GymActivitySchedule gymActivitySchedule = gymActivityScheduleRepository.findTopByGymAddressIdAndActivityIdOrderByPositionDesc(gymAddressId, activityId);
        if (gymActivitySchedule != null) {
            return gymActivitySchedule.getPosition();
        } else {
            return -1;
        }
    }

    @Override
    public void fillDescription(List<GymActivityScheduleModel> gymActivityScheduleModelList, MessageSource messageSource, Locale locale) {
        for (GymActivityScheduleModel gymActivityScheduleModel : gymActivityScheduleModelList) {
            StringBuilder sb = new StringBuilder();
            sb.append(gymActivityScheduleModel.getName()).append(" - ");
            if (gymActivityScheduleModel.isMonday()) {
                sb.append(messageSource.getMessage("monday.short", null, locale));
                if (Constants.MONDAY.equalsIgnoreCase(gymActivityScheduleModel.getLastDayOfWeek())) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
            if (gymActivityScheduleModel.isTuesday()) {
                sb.append(messageSource.getMessage("tuesday.short", null, locale));
                if (Constants.TUESDAY.equalsIgnoreCase(gymActivityScheduleModel.getLastDayOfWeek())) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
            if (gymActivityScheduleModel.isWednesday()) {
                sb.append(messageSource.getMessage("wednesday.short", null, locale));
                if (Constants.WEDNESDAY.equalsIgnoreCase(gymActivityScheduleModel.getLastDayOfWeek())) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
            if (gymActivityScheduleModel.isThursday()) {
                sb.append(messageSource.getMessage("thursday.short", null, locale));
                if (Constants.THURSDAY.equalsIgnoreCase(gymActivityScheduleModel.getLastDayOfWeek())) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
            if (gymActivityScheduleModel.isFriday()) {
                sb.append(messageSource.getMessage("friday.short", null, locale));
                if (Constants.FRIDAY.equalsIgnoreCase(gymActivityScheduleModel.getLastDayOfWeek())) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
            if (gymActivityScheduleModel.isSaturday()) {
                sb.append(messageSource.getMessage("saturday.short", null, locale));
                if (Constants.SATURDAY.equalsIgnoreCase(gymActivityScheduleModel.getLastDayOfWeek())) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
            if (gymActivityScheduleModel.isSunday()) {
                sb.append(messageSource.getMessage("sunday.short", null, locale));
                sb.append(" ");
            }
            sb.append(messageSource.getMessage("from", null, locale)).append(" ");
            sb.append(gymActivityScheduleModel.getStartTime()).append(" ");
            sb.append(messageSource.getMessage("to", null, locale)).append(" ");
            sb.append(gymActivityScheduleModel.getEndTime()).append(" - ");
            sb.append(gymActivityScheduleModel.getPrice()).append(gymActivityScheduleModel.getCurrency());
            gymActivityScheduleModel.setDescription(sb.toString());
        }
    }

    private void moveItem(Long gymAddressId, Long activityId, int position, boolean moveUp) {
        GymActivitySchedule gymActivitySchedule = gymActivityScheduleRepository.findByGymAddressIdAndActivityIdAndPosition(gymAddressId, activityId, position);
        gymActivitySchedule.setPosition(position + (moveUp ? 1 : -1));
        gymActivityScheduleRepository.save(gymActivitySchedule);
    }
}

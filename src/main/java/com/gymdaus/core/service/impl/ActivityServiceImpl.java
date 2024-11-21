package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Activity;
import com.gymdaus.core.mapper.MapperActivity;
import com.gymdaus.core.model.ActivityModel;
import com.gymdaus.core.repository.ActivityRepository;
import com.gymdaus.core.service.ActivityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MapperActivity mapperActivity;

    @Override
    public List<ActivityModel> findAll() {
        List<ActivityModel> activityModelList = new ArrayList<>();
        for (Activity activity : activityRepository.findAllByOrderByPositionAsc()) {
            activityModelList.add(mapperActivity.entity2Model(activity));
        }
        return activityModelList;
    }

    @Override
    public ActivityModel findById(Long id) {
        try {
            return mapperActivity.entity2Model(activityRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new ActivityModel();
        }
    }

    @Override
    public void add(ActivityModel activityModel) {
        activityRepository.save(mapperActivity.model2Entity(activityModel));
    }

    @Override
    public void update(ActivityModel activityModel) {
        activityRepository.save(mapperActivity.model2Entity(activityModel));
    }

    @Override
    public void delete(Long id) {
        activityRepository.deleteById(id);
        List<Activity> activityList = activityRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getPosition() != i) {
                activityList.get(i).setPosition(i);
                activityRepository.save(activityList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        Activity activity = activityRepository.findByPosition(initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(i, false);
            }
        }
        activity.setPosition(finalPosition);
        activityRepository.save(activity);
    }

    @Override
    public int findMaxPosition() {
        Activity activity = activityRepository.findTopByOrderByPositionDesc();
        if (activity != null) {
            return activity.getPosition();
        } else {
            return -1;
        }
    }

    @Override
    public List<ActivityModel> findAllEnabled() {
        List<ActivityModel> activityModelList = new ArrayList<>();
        for (Activity activity : activityRepository.findAllByEnabledTrueOrderByPositionAsc()) {
            activityModelList.add(mapperActivity.entity2Model(activity));
        }
        return activityModelList;
    }

    private void moveItem(int position, boolean moveUp) {
        Activity activity = activityRepository.findByPosition(position);
        activity.setPosition(position + (moveUp ? 1 : -1));
        activityRepository.save(activity);
    }
}

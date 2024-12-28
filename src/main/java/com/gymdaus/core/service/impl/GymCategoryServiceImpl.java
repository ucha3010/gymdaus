package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymCategory;
import com.gymdaus.core.entity.GymMoreRegistrationGymCategory;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperGymCategory;
import com.gymdaus.core.model.GymCategoryModel;
import com.gymdaus.core.repository.GymCategoryRepository;
import com.gymdaus.core.repository.GymMoreRegistrationGymCategoryRepository;
import com.gymdaus.core.service.GymCategoryGymBeltService;
import com.gymdaus.core.service.GymCategoryService;
import com.gymdaus.core.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymCategoryServiceImpl implements GymCategoryService {

    @Autowired
    private GymCategoryRepository gymCategoryRepository;

    @Autowired
    private MapperGymCategory mapperGymCategory;
    @Autowired
    private GymMoreRegistrationGymCategoryRepository gymMoreRegistrationGymCategoryRepository;
    @Autowired
    private GymCategoryGymBeltService gymCategoryGymBeltService;

    @Override
    public List<GymCategoryModel> findAllByGymId(Long gymId) {
        List<GymCategoryModel> gymCategoryModelList = new ArrayList<>();
        for (GymCategory gymCategory : gymCategoryRepository.findAllByGymIdOrderByPositionAsc(gymId)) {
            gymCategoryModelList.add(mapperGymCategory.entity2Model(gymCategory));
        }
        return gymCategoryModelList;
    }

    @Override
    public List<GymCategoryModel> findAllByGymIdAndSelected(Long gymId, Long gymMoreRegistrationId) {
        List<GymCategoryModel> gymCategoryModelList = findAllByGymId(gymId);
        List<GymMoreRegistrationGymCategory> gymMoreRegistrationGymCategoryList = gymMoreRegistrationGymCategoryRepository.findByGymMoreRegistrationId(gymMoreRegistrationId);
        for (GymCategoryModel gymCategoryModel : gymCategoryModelList) {
            for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategoryModel : gymMoreRegistrationGymCategoryList) {
                if (gymCategoryModel.getId().equals(gymMoreRegistrationGymCategoryModel.getGymCategoryId())) {
                    gymCategoryModel.setSelected(Boolean.TRUE);
                    break;
                }
            }
        }
        return gymCategoryModelList;
    }

    @Override
    public GymCategoryModel findById(Long id) {
        try {
            return mapperGymCategory.entity2Model(gymCategoryRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymCategoryModel();
        }
    }

    @Override
    public GymCategoryModel add(GymCategoryModel gymCategoryModel) {
        return mapperGymCategory.entity2Model(gymCategoryRepository.save(mapperGymCategory.model2Entity(gymCategoryModel)));
    }

    @Override
    public void update(GymCategoryModel gymCategoryModel) {
        gymCategoryRepository.save(mapperGymCategory.model2Entity(gymCategoryModel));
    }

    @Override
    public void delete(Long id) throws RemoveException {
        GymCategory gymCategory = gymCategoryRepository.findById(id).orElse(null);
        if (gymCategory != null) {
            StringBuilder errorAdvice = new StringBuilder();
            List<GymMoreRegistrationGymCategory> gymMoreRegistrationGymCategoryList = gymMoreRegistrationGymCategoryRepository.findByGymCategoryId(id);
            if (!gymMoreRegistrationGymCategoryList.isEmpty()) {
                for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory : gymMoreRegistrationGymCategoryList) {
                    errorAdvice.append(gymMoreRegistrationGymCategory.getGymMoreRegistrationId()).append(" ");
                }
                throw new RemoveException(Constants.DELETE_ADVICE, errorAdvice.toString());
            }
            try {
                gymCategoryGymBeltService.deleteByGymCategoryId(id);
                gymCategoryRepository.deleteById(id);
                List<GymCategory> gymCategoryList = gymCategoryRepository.findAllByGymIdOrderByPositionAsc(gymCategory.getGymId());
                for (int i = 0; i < gymCategoryList.size(); i++) {
                    if (gymCategoryList.get(i).getPosition() != i) {
                        gymCategoryList.get(i).setPosition(i);
                        gymCategoryRepository.save(gymCategoryList.get(i));
                    }
                }
            } catch (Exception e) {
                throw new RemoveException(Constants.DELETE_ADVICE, e.getMessage());
            }
        }
    }

    @Override
    public void dragOfPosition(Long gymId, int initialPosition, int finalPosition) {
        GymCategory gymCategory = gymCategoryRepository.findByGymIdAndPosition(gymId, initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(gymId, i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(gymId, i, false);
            }
        }
        gymCategory.setPosition(finalPosition);
        gymCategoryRepository.save(gymCategory);
    }

    @Override
    public int findMaxPosition(Long gymId) {
        GymCategory gymCategory = gymCategoryRepository.findTopByGymIdOrderByPositionDesc(gymId);
        if (gymCategory != null) {
            return gymCategory.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(Long gymId, int position, boolean moveUp) {
        GymCategory gymCategory = gymCategoryRepository.findByGymIdAndPosition(gymId, position);
        gymCategory.setPosition(position + (moveUp ? 1 : -1));
        gymCategoryRepository.save(gymCategory);
    }
}

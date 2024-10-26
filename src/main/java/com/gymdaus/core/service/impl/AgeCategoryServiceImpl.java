package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.AgeCategory;
import com.gymdaus.core.mapper.MapperAgeCategory;
import com.gymdaus.core.model.AgeCategoryModel;
import com.gymdaus.core.repository.AgeCategoryRepository;
import com.gymdaus.core.service.AgeCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class AgeCategoryServiceImpl implements AgeCategoryService {

    @Autowired
    private AgeCategoryRepository ageCategoryRepository;

    @Autowired
    private MapperAgeCategory mapperAgeCategory;

    @Override
    public List<AgeCategoryModel> findAll() {
        List<AgeCategoryModel> ageCategoryModelList = new ArrayList<>();
        for (AgeCategory ageCategory : ageCategoryRepository.findAllByOrderByPositionAsc()) {
            ageCategoryModelList.add(mapperAgeCategory.entity2Model(ageCategory));
        }
        return ageCategoryModelList;
    }

    @Override
    public AgeCategoryModel findById(Long id) {
        try {
            return mapperAgeCategory.entity2Model(ageCategoryRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new AgeCategoryModel();
        }
    }

    @Override
    public void add(AgeCategoryModel ageCategoryModel) {
        ageCategoryRepository.save(mapperAgeCategory.model2Entity(ageCategoryModel));
    }

    @Override
    public void update(AgeCategoryModel ageCategoryModel) {
        ageCategoryRepository.save(mapperAgeCategory.model2Entity(ageCategoryModel));
    }

    @Override
    public void delete(Long id) {
        ageCategoryRepository.deleteById(id);
        List<AgeCategory> ageCategoryList = ageCategoryRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < ageCategoryList.size(); i++) {
            if (ageCategoryList.get(i).getPosition() != i) {
                ageCategoryList.get(i).setPosition(i);
                ageCategoryRepository.save(ageCategoryList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        AgeCategory ageCategory = ageCategoryRepository.findByPosition(initialPosition);
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
        ageCategory.setPosition(finalPosition);
        ageCategoryRepository.save(ageCategory);
    }

    @Override
    public int findMaxPosition() {
        AgeCategory ageCategory = ageCategoryRepository.findTopByOrderByPositionDesc();
        if (ageCategory != null) {
            return ageCategory.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        AgeCategory ageCategory = ageCategoryRepository.findByPosition(position);
        ageCategory.setPosition(position + (moveUp ? 1 : -1));
        ageCategoryRepository.save(ageCategory);
    }
}

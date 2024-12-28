package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationParticipatingEntity;
import com.gymdaus.core.entity.ParticipatingEntity;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperParticipatingEntity;
import com.gymdaus.core.model.ParticipatingEntityModel;
import com.gymdaus.core.repository.GymMoreRegistrationParticipatingEntityRepository;
import com.gymdaus.core.repository.ParticipatingEntityRepository;
import com.gymdaus.core.service.ParticipatingEntityService;
import com.gymdaus.core.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class ParticipatingEntityServiceImpl implements ParticipatingEntityService {

    @Autowired
    private ParticipatingEntityRepository participatingEntityRepository;

    @Autowired
    private MapperParticipatingEntity mapperParticipatingEntity;
    @Autowired
    private GymMoreRegistrationParticipatingEntityRepository gymMoreRegistrationParticipatingEntityRepository;

    @Override
    public List<ParticipatingEntityModel> findAllByGymId(Long gymId) {
        List<ParticipatingEntityModel> participatingEntityModelList = new ArrayList<>();
        for (ParticipatingEntity participatingEntity : participatingEntityRepository.findAllByGymIdOrderByPositionAsc(gymId)) {
            participatingEntityModelList.add(mapperParticipatingEntity.entity2Model(participatingEntity));
        }
        return participatingEntityModelList;
    }

    @Override
    public ParticipatingEntityModel findById(Long id) {
        try {
            return mapperParticipatingEntity.entity2Model(participatingEntityRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new ParticipatingEntityModel();
        }
    }

    @Override
    public void add(ParticipatingEntityModel participatingEntityModel) {
        participatingEntityRepository.save(mapperParticipatingEntity.model2Entity(participatingEntityModel));
    }

    @Override
    public void update(ParticipatingEntityModel participatingEntityModel) {
        participatingEntityRepository.save(mapperParticipatingEntity.model2Entity(participatingEntityModel));
    }

    @Override
    public void delete(Long id) throws RemoveException {
        ParticipatingEntity participatingEntity = participatingEntityRepository.findById(id).orElse(null);
        if (participatingEntity != null) {
            StringBuilder errorAdvice = new StringBuilder();
            List<GymMoreRegistrationParticipatingEntity> gymMoreRegistrationParticipatingEntityList = gymMoreRegistrationParticipatingEntityRepository.findAllByParticipatingEntityId(id);
            if (!gymMoreRegistrationParticipatingEntityList.isEmpty()) {
                for (GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity : gymMoreRegistrationParticipatingEntityList) {
                    errorAdvice.append(gymMoreRegistrationParticipatingEntity.getGymMoreRegistrationId()).append(" ");
                }
                throw new RemoveException(Constants.DELETE_ADVICE, errorAdvice.toString());
            }
            try {
                participatingEntityRepository.deleteById(id);
                List<ParticipatingEntity> participatingEntityList = participatingEntityRepository.findAllByGymIdOrderByPositionAsc(participatingEntity.getGymId());
                for (int i = 0; i < participatingEntityList.size(); i++) {
                    if (participatingEntityList.get(i).getPosition() != i) {
                        participatingEntityList.get(i).setPosition(i);
                        participatingEntityRepository.save(participatingEntityList.get(i));
                    }
                }
            } catch (Exception e) {
                throw new RemoveException(Constants.DELETE_ADVICE, e.getMessage());
            }
        }
    }

    @Override
    public void dragOfPosition(Long gymId, int initialPosition, int finalPosition) {
        ParticipatingEntity participatingEntity = participatingEntityRepository.findByGymIdAndPosition(gymId, initialPosition);
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
        participatingEntity.setPosition(finalPosition);
        participatingEntityRepository.save(participatingEntity);
    }

    @Override
    public int findMaxPosition(Long gymId) {
        ParticipatingEntity participatingEntity = participatingEntityRepository.findTopByGymIdOrderByPositionDesc(gymId);
        if (participatingEntity != null) {
            return participatingEntity.getPosition();
        } else {
            return -1;
        }
    }

    @Override
    public List<ParticipatingEntityModel> findAllByGymIdAndSelected(Long gymId, Long gymMoreRegistrationId) {
        List<ParticipatingEntityModel> participatingEntityModelList = findAllByGymId(gymId);
        List<GymMoreRegistrationParticipatingEntity> gymMoreRegistrationParticipatingEntityList = gymMoreRegistrationParticipatingEntityRepository.findAllByGymMoreRegistrationIdOrderByPositionAsc(gymMoreRegistrationId);
        for (ParticipatingEntityModel participatingEntityModel : participatingEntityModelList) {
            for (GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity : gymMoreRegistrationParticipatingEntityList) {
                if (participatingEntityModel.getId().equals(gymMoreRegistrationParticipatingEntity.getParticipatingEntityId())) {
                    participatingEntityModel.setSelected(Boolean.TRUE);
                    break;
                }
            }
        }
        return participatingEntityModelList;
    }

    private void moveItem(Long gymId, int position, boolean moveUp) {
        ParticipatingEntity participatingEntity = participatingEntityRepository.findByGymIdAndPosition(gymId, position);
        participatingEntity.setPosition(position + (moveUp ? 1 : -1));
        participatingEntityRepository.save(participatingEntity);
    }
}

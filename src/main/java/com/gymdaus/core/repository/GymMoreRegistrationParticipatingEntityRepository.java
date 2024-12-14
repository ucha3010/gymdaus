package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationParticipatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymMoreRegistrationParticipatingEntityRepository extends JpaRepository<GymMoreRegistrationParticipatingEntity, Long> {

    List<GymMoreRegistrationParticipatingEntity> findAllByOrderByPositionAsc();

    List<GymMoreRegistrationParticipatingEntity> findAllByGymMoreRegistrationIdOrderByRegistrationDateDesc(Long gymMoreRegistrationId);

    GymMoreRegistrationParticipatingEntity findByPosition(int position);

    GymMoreRegistrationParticipatingEntity findTopByOrderByPositionDesc();

}
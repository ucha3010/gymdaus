package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationParticipatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymMoreRegistrationParticipatingEntityRepository extends JpaRepository<GymMoreRegistrationParticipatingEntity, Long> {

}
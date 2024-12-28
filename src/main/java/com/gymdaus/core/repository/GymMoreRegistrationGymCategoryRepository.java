package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationGymCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymMoreRegistrationGymCategoryRepository extends JpaRepository<GymMoreRegistrationGymCategory, Long> {

    List<GymMoreRegistrationGymCategory> findByGymMoreRegistrationId(Long gymMoreRegistrationId);
    List<GymMoreRegistrationGymCategory> findByGymCategoryId(Long gymCategoryId);
}
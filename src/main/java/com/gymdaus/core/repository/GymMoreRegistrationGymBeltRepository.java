package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationGymBelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymMoreRegistrationGymBeltRepository extends JpaRepository<GymMoreRegistrationGymBelt, Long> {

    List<GymMoreRegistrationGymBelt> findAllByOrderByPositionAsc();

    List<GymMoreRegistrationGymBelt> findAllByGymMoreRegistrationIdOrderByPositionAsc(Long gymMoreRegistrationId);

}
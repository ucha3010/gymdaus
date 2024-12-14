package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationBelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymMoreRegistrationBeltRepository extends JpaRepository<GymMoreRegistrationBelt, Long> {

    List<GymMoreRegistrationBelt> findAllByOrderByPositionAsc();

    List<GymMoreRegistrationBelt> findAllByGymMoreRegistrationId(Long gymMoreRegistrationId);

    GymMoreRegistrationBelt findByPosition(int position);

    GymMoreRegistrationBelt findTopByOrderByPositionDesc();

}
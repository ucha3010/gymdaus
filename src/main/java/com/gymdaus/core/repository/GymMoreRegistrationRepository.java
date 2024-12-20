package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymMoreRegistrationRepository extends JpaRepository<GymMoreRegistration, Long> {

    List<GymMoreRegistration> findAllByGymIdAndEnabledTrueOrderByRegistrationDateAsc(Long gymId);

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymMoreRegistrationRepository extends JpaRepository<GymMoreRegistration, Long> {

}
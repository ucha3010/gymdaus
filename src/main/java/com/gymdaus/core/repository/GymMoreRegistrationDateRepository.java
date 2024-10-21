package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymMoreRegistrationDateRepository extends JpaRepository<GymMoreRegistrationDate, Long> {

}
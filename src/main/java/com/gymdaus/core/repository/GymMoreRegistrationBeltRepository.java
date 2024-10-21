package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymMoreRegistrationBelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymMoreRegistrationBeltRepository extends JpaRepository<GymMoreRegistrationBelt, Long> {

}
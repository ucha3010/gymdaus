package com.gymdaus.core.repository;

import com.gymdaus.core.entity.MoreRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface MoreRegistrationRepository extends JpaRepository<MoreRegistration, Long> {

}
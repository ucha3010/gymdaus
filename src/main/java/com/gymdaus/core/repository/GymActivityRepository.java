package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymActivityRepository extends JpaRepository<GymActivity, Long> {

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymActivitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymActivityScheduleRepository extends JpaRepository<GymActivitySchedule, Long> {

}
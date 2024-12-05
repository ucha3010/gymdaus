package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymActivityRepository extends JpaRepository<GymActivity, Long> {

    List<GymActivity> findAllByGymIdOrderByRegistrationDateAsc(Long gymId);

}
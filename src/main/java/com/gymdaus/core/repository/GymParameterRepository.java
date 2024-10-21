package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymParameterRepository extends JpaRepository<GymParameter, Long> {

}
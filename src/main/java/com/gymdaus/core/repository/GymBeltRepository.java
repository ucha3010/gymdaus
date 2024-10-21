package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymBelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymBeltRepository extends JpaRepository<GymBelt, Long> {

}
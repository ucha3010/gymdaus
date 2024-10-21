package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymUserRepository extends JpaRepository<GymUser, Long> {

}
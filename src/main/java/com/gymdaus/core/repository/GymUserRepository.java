package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymUserRepository extends JpaRepository<GymUser, Long> {

    List<GymUser> findByUsername(String username);
    List<GymUser> findByGymId(Long id);
}
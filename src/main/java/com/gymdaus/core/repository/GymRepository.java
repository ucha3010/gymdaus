package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
public interface GymRepository extends JpaRepository<Gym, Long> {

    List<Gym> findAllByEnabledTrueOrderByPositionAsc();
    Optional<Gym> findByIdAndEnabledTrue(Long id);
}
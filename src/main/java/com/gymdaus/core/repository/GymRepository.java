package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
public interface GymRepository extends JpaRepository<Gym, Long> {

    List<Gym> findAllByOrderByPositionAsc();

    List<Gym> findAllByEnabledTrueOrderByPositionAsc();

    Optional<Gym> findByIdAndEnabledTrue(Long id);

    @Query("SELECT m.id FROM Gym m WHERE m.enabled = true")
    List<Long> findIdsByEnabledTrue();

    Gym findByPosition(int position);

    Gym findTopByOrderByPositionDesc();
    @Query("SELECT m.name FROM Gym m WHERE m.id = :id")
    String getNameById(@Param("id") Long id);
}
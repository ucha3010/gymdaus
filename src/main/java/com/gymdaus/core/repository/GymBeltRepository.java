package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymBelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymBeltRepository extends JpaRepository<GymBelt, Long> {
    List<GymBelt> findAllByGymIdOrderByPositionAsc(Long gymId);

    GymBelt findByGymIdAndPosition(Long gymId, int position);

    GymBelt findTopByGymIdOrderByPositionDesc(Long gymId);

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymCategoryRepository extends JpaRepository<GymCategory, Long> {
    List<GymCategory> findAllByGymIdOrderByPositionAsc(Long gymId);

    GymCategory findByGymIdAndPosition(Long gymId, int position);

    GymCategory findTopByGymIdOrderByPositionDesc(Long gymId);

}
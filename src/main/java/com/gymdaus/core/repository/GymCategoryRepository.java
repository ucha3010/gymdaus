package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymCategoryRepository extends JpaRepository<GymCategory, Long> {
    List<GymCategory> findAllByOrderByPositionAsc();

    GymCategory findByPosition(int position);

    GymCategory findTopByOrderByPositionDesc();

}
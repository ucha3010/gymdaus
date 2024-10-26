package com.gymdaus.core.repository;

import com.gymdaus.core.entity.AgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface AgeCategoryRepository extends JpaRepository<AgeCategory, Long> {
    List<AgeCategory> findAllByOrderByPositionAsc();

    AgeCategory findByPosition(int position);

    AgeCategory findTopByOrderByPositionDesc();

}
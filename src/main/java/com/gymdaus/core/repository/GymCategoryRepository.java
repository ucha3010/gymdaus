package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymCategoryRepository extends JpaRepository<GymCategory, Long> {

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.AgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface AgeCategoryRepository extends JpaRepository<AgeCategory, Long> {

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymCategoryGymBelt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymCategoryGymBeltRepository extends JpaRepository<GymCategoryGymBelt, Long> {
    List<GymCategoryGymBelt> findByGymCategoryId(Long gymCategoryId);

    List<GymCategoryGymBelt> findByGymBeltId(Long gymBeltId);

    GymCategoryGymBelt findByGymCategoryIdAndGymBeltId(Long gymCategoryId, Long gymBeltId);

    void deleteAllByGymCategoryId(Long gymCategoryId);

}
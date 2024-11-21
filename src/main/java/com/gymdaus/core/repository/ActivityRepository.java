package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllByOrderByPositionAsc();

    List<Activity> findAllByEnabledTrueOrderByPositionAsc();

    Activity findByPosition(int position);

    Activity findTopByOrderByPositionDesc();

}
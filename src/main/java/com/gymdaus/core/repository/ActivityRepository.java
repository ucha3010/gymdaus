package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
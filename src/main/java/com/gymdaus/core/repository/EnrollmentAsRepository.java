package com.gymdaus.core.repository;

import com.gymdaus.core.entity.EnrollmentAs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface EnrollmentAsRepository extends JpaRepository<EnrollmentAs, Long> {
    List<EnrollmentAs> findAllByOrderByPositionAsc();

    EnrollmentAs findByPosition(int position);

    EnrollmentAs findTopByOrderByPositionDesc();

}
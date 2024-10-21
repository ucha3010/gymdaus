package com.gymdaus.core.repository;

import com.gymdaus.core.entity.EnrollmentAs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface EnrollmentAsRepository extends JpaRepository<EnrollmentAs, Long> {

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
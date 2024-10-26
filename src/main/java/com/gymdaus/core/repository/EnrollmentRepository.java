package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUsername(String username);
    List<Enrollment> findByGymId(Long gymId);

}
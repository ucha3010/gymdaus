package com.gymdaus.core.repository;

import com.gymdaus.core.entity.EnrollmentMoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface EnrollmentMoreDataRepository extends JpaRepository<EnrollmentMoreData, Long> {

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.ManagerParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ManagerParameterRepository extends JpaRepository<ManagerParameter, Long> {

}
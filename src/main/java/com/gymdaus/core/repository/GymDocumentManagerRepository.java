package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymDocumentManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymDocumentManagerRepository extends JpaRepository<GymDocumentManager, Long> {

}
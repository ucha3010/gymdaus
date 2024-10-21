package com.gymdaus.core.repository;

import com.gymdaus.core.entity.ParticipatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ParticipatingEntityRepository extends JpaRepository<ParticipatingEntity, Long> {

}
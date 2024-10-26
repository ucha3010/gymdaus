package com.gymdaus.core.repository;

import com.gymdaus.core.entity.ParticipatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface ParticipatingEntityRepository extends JpaRepository<ParticipatingEntity, Long> {
    List<ParticipatingEntity> findAllByOrderByPositionAsc();

    ParticipatingEntity findByPosition(int position);

    ParticipatingEntity findTopByOrderByPositionDesc();

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymPoomsae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymPoomsaeRepository extends JpaRepository<GymPoomsae, Long> {
    List<GymPoomsae> findAllByGymIdOrderByPositionAsc(Long gymId);

    GymPoomsae findByGymIdAndPosition(Long gymId, int position);

    GymPoomsae findTopByGymIdOrderByPositionDesc(Long gymId);

}
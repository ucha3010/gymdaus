package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymPoomsae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymPoomsaeRepository extends JpaRepository<GymPoomsae, Long> {

}
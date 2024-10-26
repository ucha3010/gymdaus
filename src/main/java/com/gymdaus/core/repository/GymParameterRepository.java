package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymParameterRepository extends JpaRepository<GymParameter, Long> {
    List<GymParameter> findAllByGymId(Long gymId);
    GymParameter findByGymIdAndKeyData(Long gymId, String keyData);

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymPhotoRepository extends JpaRepository<GymPhoto, Long> {

    List<GymPhoto> findByGymIdOrderByMainPhotoDesc(Long gymId);

    GymPhoto findByGymIdAndMainPhotoTrue(Long gymId);
}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GymAddressRepository extends JpaRepository<GymAddress, Long> {

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface SignatureRepository extends JpaRepository<Signature, Long> {

}
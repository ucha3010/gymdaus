package com.gymdaus.core.repository;

import com.gymdaus.core.entity.SignatureCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface SignatureCodeRepository extends JpaRepository<SignatureCode, Long> {

}
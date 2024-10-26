package com.gymdaus.core.repository;

import com.gymdaus.core.entity.SignatureCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface SignatureCodeRepository extends JpaRepository<SignatureCode, Long> {

    List<SignatureCode> findByOperationId(Long operationId);

    List<SignatureCode> findAllByOrderByExpirationDateDesc();

}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface TokenRepository extends JpaRepository<Token, String> {

    List<Token> findAllByOrderByExpirationAsc();

}
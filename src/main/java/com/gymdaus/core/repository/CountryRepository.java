package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAllByOrderByPositionAsc();

    Country findByPosition(int position);

    Country findTopByOrderByPositionDesc();
}
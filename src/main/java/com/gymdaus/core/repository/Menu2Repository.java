package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Menu2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface Menu2Repository extends JpaRepository<Menu2, Long> {

    Menu2 findTopByMenu1IdOrderByPositionDesc(Long menu1Id);

    List<Menu2> findByMenu1IdOrderByPositionAsc(Long menu1Id);

    Menu2 findByMenu1IdAndPosition(Long menu1Id, int position);

    Menu2 findByUrl(String url);
}
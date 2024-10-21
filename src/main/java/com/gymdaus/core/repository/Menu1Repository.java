package com.gymdaus.core.repository;

import com.gymdaus.core.entity.Menu1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface Menu1Repository extends JpaRepository<Menu1, Long> {

    List<Menu1> findAllByOrderByPositionAsc();

    Menu1 findByPosition(int position);

    Menu1 findTopByOrderByPositionDesc();
}
package com.gymdaus.core.repository;

import com.gymdaus.core.entity.GymActivitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface GymActivityScheduleRepository extends JpaRepository<GymActivitySchedule, Long> {

    List<GymActivitySchedule> findAllByOrderByPositionAsc();

    List<GymActivitySchedule> findAllByGymAddressIdAndActivityIdOrderByPositionAsc(Long gymAddressId, Long activityId);

    GymActivitySchedule findByGymAddressIdAndActivityIdAndPosition(Long gymAddressId, Long activityId, int position);

    GymActivitySchedule findTopByGymAddressIdAndActivityIdOrderByPositionDesc(Long gymAddressId, Long activityId);

    List<GymActivitySchedule> findAllByGymActivityIdAndAdultTrue(Long gymActivityId);

    List<GymActivitySchedule> findAllByGymActivityIdAndMinorTrue(Long gymActivityId);

    List<GymActivitySchedule> findAllByGymActivityIdAndInclusiveTrue(Long gymActivityId);
}
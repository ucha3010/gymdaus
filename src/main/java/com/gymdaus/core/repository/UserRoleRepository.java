package com.gymdaus.core.repository;

import com.gymdaus.core.entity.User;
import com.gymdaus.core.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository()
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Set<UserRole> findByUser(User user);

    void deleteByUserRoleId(Long userRoleId);
}
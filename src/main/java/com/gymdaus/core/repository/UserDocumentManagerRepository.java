package com.gymdaus.core.repository;

import com.gymdaus.core.entity.UserDocumentManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserDocumentManagerRepository extends JpaRepository<UserDocumentManager, Long> {

}
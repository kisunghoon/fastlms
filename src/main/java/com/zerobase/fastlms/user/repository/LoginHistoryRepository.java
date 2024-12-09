package com.zerobase.fastlms.user.repository;

import com.zerobase.fastlms.user.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    List<LoginHistory> findByUserIdOrderByIdDesc(String user_id);
}

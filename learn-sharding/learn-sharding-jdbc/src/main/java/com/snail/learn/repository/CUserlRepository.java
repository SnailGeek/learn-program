package com.snail.learn.repository;

import com.snail.learn.entity.CUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CUserlRepository extends JpaRepository<CUser, Long> {

    List<CUser> findByPwd(String pwd);
}

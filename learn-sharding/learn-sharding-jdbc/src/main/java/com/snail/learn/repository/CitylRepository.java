package com.snail.learn.repository;

import com.snail.learn.entity.City;
import com.snail.learn.entity.PositionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitylRepository extends JpaRepository<City, Long> {

}

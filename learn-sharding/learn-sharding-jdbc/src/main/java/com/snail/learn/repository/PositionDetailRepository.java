package com.snail.learn.repository;

import com.snail.learn.entity.Position;
import com.snail.learn.entity.PositionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionDetailRepository extends JpaRepository<PositionDetail, Long> {

}

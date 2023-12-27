package com.snail.learn.repository;

import com.snail.learn.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {

}

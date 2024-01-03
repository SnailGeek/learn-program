package com.snail.learn.repository;

import com.snail.learn.entity.BOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BOrderRepository extends JpaRepository<BOrder, Long> {

}

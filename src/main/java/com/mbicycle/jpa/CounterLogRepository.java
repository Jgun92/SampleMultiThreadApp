package com.mbicycle.jpa;

import com.mbicycle.model.entity.CounterLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterLogRepository extends JpaRepository<CounterLogEntity, Long> {


}

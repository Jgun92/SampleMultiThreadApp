package com.mbicycle.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode

public class CounterLogEntity {
    @Id
    private Long id;
    private String className;
    private Long timestamp;
}

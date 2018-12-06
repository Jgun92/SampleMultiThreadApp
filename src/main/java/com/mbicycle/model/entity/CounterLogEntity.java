package com.mbicycle.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CounterLogEntity {
    @Id
    private Long id;
    private String className;
    private Long timestamp;
    private int value;

    public CounterLogEntity(Runnable className, int value) {
        this.className = String.valueOf(className);
        this.timestamp = System.currentTimeMillis();
        this.value = value;
    }
}

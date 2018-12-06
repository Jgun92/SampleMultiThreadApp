package com.mbicycle.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class RequestLog {
    @Id
    private Long id;
    private String log;

    public RequestLog(String log) {
        this.log = log;
    }
}

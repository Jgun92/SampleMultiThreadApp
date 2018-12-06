package com.mbicycle.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCounterResponse {
    private Integer currentValue;
    private Integer previousValue;

}

package com.mbicycle.service.producer;

import com.mbicycle.service.Counter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Producer implements Runnable {
    private Counter counter;

    public Producer(Counter counter) {
        this.counter = counter;
    }


    @Override
    public void run() {
        Integer result;
        try {
            while (!counter.canDecrement()) {
                Thread.sleep(500);
            }
            result = counter.decrement();
            log.info("current result is {}", result);
            if (result.equals(0)) {
                log.info("{} get counter to to low value. Event time {}", this, System.currentTimeMillis());
            }
            notify();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
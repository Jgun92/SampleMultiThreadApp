package com.mbicycle.service.consumer;

import com.mbicycle.service.Counter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Consumer implements Runnable {

    private Counter counter;


    public Consumer(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        Integer result;
        try {
            while (!counter.canIncrement()) {
                Thread.sleep(500);

            }
            result = counter.increment();
            log.info("current result is {}", result);
            if (result.equals(100)) {
                log.info("{} ,increment counter to to height value  {}", this, System.currentTimeMillis());
            }
            notify();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}

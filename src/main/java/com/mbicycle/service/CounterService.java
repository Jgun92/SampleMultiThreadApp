package com.mbicycle.service;


import com.mbicycle.service.consumer.Consumer;
import com.mbicycle.service.producer.Producer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Data
@Slf4j
public class CounterService {
    private ThreadPoolExecutor consumerPoolExecutor = new ThreadPoolExecutor(4, 10, 7, TimeUnit.DAYS, new LinkedBlockingDeque<>());
    private ThreadPoolExecutor producerPoolExecutor = new ThreadPoolExecutor(4, 10, 7, TimeUnit.DAYS, new LinkedBlockingDeque<>());
    @Autowired
    private Counter service;


    public void addConsumerAndProducer(int consumer, int producer) {
        while (consumer > 0) {
            addConsumer();
            consumer--;
        }
        while (producer > 0) {
            addProducer();
            producer--;
        }
    }



    private void addConsumer() {
        consumerPoolExecutor.submit(new Consumer(service));
    }

    private void addProducer() {
        producerPoolExecutor.submit(new Producer(service));
    }

    public void setCounterValue(int value) {
        service.setValue(value);

    }
}
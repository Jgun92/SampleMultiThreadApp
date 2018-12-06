package com.mbicycle.service;


import com.mbicycle.jpa.CounterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class Counter {

    @Autowired
    private CounterLogRepository counterLogRepository;

    private static AtomicInteger counter = new AtomicInteger(50);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();


    public synchronized Integer increment() {
        try {
            lock.writeLock().lock();

            while (true) {
                return counter.incrementAndGet();
            }
        } finally {
            lock.writeLock().unlock();
        }

    }
    public void setValue(Integer newValue) {
        try {
            lock.writeLock().lock();

            while (true) {
                int existingValue = counter.get();
                if (counter.compareAndSet(existingValue, newValue)) {
                    return;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }

    }

    public synchronized Integer decrement() {
        try {
            lock.writeLock().lock();

            while (true) {
                int value = counter.decrementAndGet();
                return value;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean canIncrement() {
        return counter.get() < 100;
    }

    public boolean canDecrement() {
        return counter.get() > 0;
    }
}
package com.mbicycle.controller;

import com.mbicycle.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ThreadsController {
    @Autowired
    private CounterService clientService;

    @GetMapping("/counter")
    public ResponseEntity threadIncrementor(@RequestParam(value = "incr_consumers", required = false, defaultValue = "0") int increment, @RequestParam(value = "incr_producers", required =false, defaultValue = "0") int decrement) {

        clientService.addConsumerAndProducer(increment, decrement);
        return ResponseEntity.created(null).build();

    }

    @PatchMapping("/counter/{value}")

    public ResponseEntity setCount(@PathVariable int value) {
        clientService.setCounterValue(value);
        return ResponseEntity.ok().build();
    }


}

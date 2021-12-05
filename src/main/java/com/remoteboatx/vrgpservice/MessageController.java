package com.remoteboatx.vrgpservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MessageController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/message")
    public Message greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Message(counter.incrementAndGet(), "Toy", String.format(template, name));
    }
}
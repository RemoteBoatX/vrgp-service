package com.remoteboatx.moc;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Message greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Message(counter.incrementAndGet(),"Toy", String.format(template, name));
    }
}

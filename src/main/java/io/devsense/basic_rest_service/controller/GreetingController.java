package io.devsense.basic_rest_service.controller;

import io.devsense.basic_rest_service.domain.model.Greeting;
import io.devsense.basic_rest_service.domain.model.GreetingOldref;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s !";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "http://localhost:9001")
    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/greeting-javaconfig")
    public Greeting greetingWithJavaconfig(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/greeting-oldref-javaconfig")
    public GreetingOldref greetingOldrefWithJavaconfig(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting ====");
        return new GreetingOldref(String.format(template, name));
    }
}

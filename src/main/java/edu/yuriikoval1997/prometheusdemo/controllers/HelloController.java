package edu.yuriikoval1997.prometheusdemo.controllers;

import edu.yuriikoval1997.prometheusdemo.aspects.YuriiCounted;
import io.prometheus.client.Counter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Getter
@RestController
public class HelloController {

    private final Counter helloWorldCounter;

    @Autowired
    public HelloController(Counter helloWorldCounter) {
        this.helloWorldCounter = helloWorldCounter;
    }

    @RequestMapping(value = "/hello", method = GET)
    public ResponseEntity<Map<String, Object>> helloGet() {
        double random = Math.random();
        if (random > 0.5) {
            helloWorldCounter.labels("/hello", "GET", "400").inc();
            return ResponseEntity.status(422).body(Map.of("message", "GET"));
        }
        helloWorldCounter.labels("/hello", "GET", "200").inc();
        return ResponseEntity.ok().body(Map.of("message", "GET"));
    }

    @RequestMapping(value = "/hello", method = POST)
    public ResponseEntity<Map<String, Object>> helloPost() {
        helloWorldCounter.labels("/hello", "POST", "201").inc();
        return ResponseEntity.ok().body(Map.of("message", "POST"));
    }

    @YuriiCounted(labels = {"/hello", "PUT"})
    @RequestMapping(value = "/hello", method = PUT)
    public ResponseEntity<Map<String, Object>> annotated() {
        throw new RuntimeException("Haha");
    }
}

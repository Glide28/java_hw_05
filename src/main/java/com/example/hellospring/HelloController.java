package com.example.hellospring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    private final MessageService service;
    public HelloController(MessageService service) { this.service = service; }
    @GetMapping("/") public String hello() { return "Hello, World!"; }
    @GetMapping("/messages") public List<Message> getMessages() { return service.getAllMessages(); }
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createMessage(message));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidation(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
    }
}

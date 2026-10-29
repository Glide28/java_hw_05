package com.example.hellospring;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository repository;
    public MessageService(MessageRepository repository) {
        this.repository = repository;
        repository.save(new Message("Hello, Spring!", "System"));
        repository.save(new Message("In-memory storage works", "System"));
    }
    public List<Message> getAllMessages() { return repository.findAll(); }
    public Message createMessage(Message message) {
        if (message.getContent() == null || message.getContent().isBlank()) throw new IllegalArgumentException("Message content must not be empty");
        if (message.getAuthor() == null || message.getAuthor().isBlank()) throw new IllegalArgumentException("Message author must not be empty");
        message.setId(null);
        message.setContent(message.getContent().trim());
        message.setAuthor(message.getAuthor().trim());
        return repository.save(message);
    }
}

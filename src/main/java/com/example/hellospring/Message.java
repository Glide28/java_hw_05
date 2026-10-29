package com.example.hellospring;

import java.util.Objects;

public class Message {
    private Long id;
    private String content;
    private String author;

    public Message() {}
    public Message(String content, String author) { this.content = content; this.author = author; }
    public Message(Long id, String content, String author) { this.id = id; this.content = content; this.author = author; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    @Override public boolean equals(Object o) { return this == o || o instanceof Message m && Objects.equals(id, m.id); }
    @Override public int hashCode() { return Objects.hash(id); }
}

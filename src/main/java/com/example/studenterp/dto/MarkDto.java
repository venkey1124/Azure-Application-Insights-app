package com.example.studenterp.dto;

public class MarkDto {

    private String subject;
    private Integer marks;

    public MarkDto(String subject, Integer marks) {
        this.subject = subject;
        this.marks = marks;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getMarks() {
        return marks;
    }
}

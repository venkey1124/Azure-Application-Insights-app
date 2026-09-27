package com.example.studenterp.dto;

public class AttendanceDto {

    private String subject;
    private Double percentage;

    public AttendanceDto(String subject, Double percentage) {
        this.subject = subject;
        this.percentage = percentage;
    }

    public String getSubject() {
        return subject;
    }

    public Double getPercentage() {
        return percentage;
    }
}

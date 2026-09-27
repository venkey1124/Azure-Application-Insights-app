package com.example.studenterp.dto;

public class LoginResponse {

    private boolean success;
    private String message;
    private Long studentId;

    public LoginResponse(boolean success, String message, Long studentId) {
        this.success = success;
        this.message = message;
        this.studentId = studentId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

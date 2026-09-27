package com.example.studenterp.dto;

public class StudentProfileDto {

    private Long id;
    private String name;
    private String email;
    private String department;
    private Integer year;

    public StudentProfileDto(Long id, String name, String email, String department, Integer year) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getYear() {
        return year;
    }
}

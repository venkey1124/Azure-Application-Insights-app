package com.example.studenterp.controller;

import com.example.studenterp.dto.AttendanceDto;
import com.example.studenterp.dto.MarkDto;
import com.example.studenterp.dto.StudentProfileDto;
import com.example.studenterp.service.StudentService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public StudentProfileDto getProfile(@PathVariable Long id) {
        return studentService.getProfile(id);
    }

    @GetMapping("/{id}/marks")
    public List<MarkDto> getMarks(@PathVariable Long id) {
        return studentService.getMarks(id);
    }

    @GetMapping("/{id}/attendance")
    public List<AttendanceDto> getAttendance(@PathVariable Long id) {
        return studentService.getAttendance(id);
    }
}

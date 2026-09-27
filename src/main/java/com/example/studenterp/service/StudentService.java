package com.example.studenterp.service;

import com.example.studenterp.dto.AttendanceDto;
import com.example.studenterp.dto.MarkDto;
import com.example.studenterp.dto.StudentProfileDto;
import com.example.studenterp.entity.Student;
import com.example.studenterp.exception.ResourceNotFoundException;
import com.example.studenterp.repository.AttendanceRepository;
import com.example.studenterp.repository.MarkRepository;
import com.example.studenterp.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MarkRepository markRepository;
    private final AttendanceRepository attendanceRepository;
    private final long marksDatabaseDelayMs;

    public StudentService(StudentRepository studentRepository, MarkRepository markRepository,
            AttendanceRepository attendanceRepository,
            @Value("${app.marks.db-delay-ms}") long marksDatabaseDelayMs) {
        this.studentRepository = studentRepository;
        this.markRepository = markRepository;
        this.attendanceRepository = attendanceRepository;
        this.marksDatabaseDelayMs = marksDatabaseDelayMs;
    }

    public StudentProfileDto getProfile(Long studentId) {
        Student student = findStudent(studentId);

        return new StudentProfileDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDepartment(),
                student.getYear());
    }

    public List<MarkDto> getMarks(Long studentId) {
        findStudent(studentId);
        runMarksDatabaseDelay();

        return markRepository.findByStudentId(studentId)
                .stream()
                .map(mark -> new MarkDto(mark.getSubject(), mark.getMarks()))
                .toList();
    }

    public List<AttendanceDto> getAttendance(Long studentId) {
        findStudent(studentId);

        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(attendance -> new AttendanceDto(attendance.getSubject(), attendance.getPercentage()))
                .toList();
    }

    private Student findStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
    }

    private void runMarksDatabaseDelay() {
        if (marksDatabaseDelayMs <= 0) {
            return;
        }

        double delaySeconds = marksDatabaseDelayMs / 1000.0;
        markRepository.runControlledDatabaseDelay(delaySeconds);
    }
}

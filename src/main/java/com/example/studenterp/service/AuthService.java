package com.example.studenterp.service;

import com.example.studenterp.dto.LoginRequest;
import com.example.studenterp.dto.LoginResponse;
import com.example.studenterp.entity.Student;
import com.example.studenterp.exception.InvalidLoginException;
import com.example.studenterp.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentRepository studentRepository;

    public AuthService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public LoginResponse login(LoginRequest request) {
        Student student = studentRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));

        if (!student.getPassword().equals(request.getPassword())) {
            throw new InvalidLoginException("Invalid username or password");
        }

        return new LoginResponse(true, "Login successful", student.getId());
    }
}

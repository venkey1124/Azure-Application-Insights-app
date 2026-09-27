package com.example.studenterp.repository;

import com.example.studenterp.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUsername(String username);
}

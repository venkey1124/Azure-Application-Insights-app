package com.example.studenterp.repository;

import com.example.studenterp.entity.Attendance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);
}

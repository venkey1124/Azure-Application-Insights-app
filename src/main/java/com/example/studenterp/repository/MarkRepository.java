package com.example.studenterp.repository;

import com.example.studenterp.entity.Mark;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findByStudentId(Long studentId);

    @Query(value = "SELECT SLEEP(:delaySeconds)", nativeQuery = true)
    Integer runControlledDatabaseDelay(@Param("delaySeconds") double delaySeconds);
}

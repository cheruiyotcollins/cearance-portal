package com.clearance.gigster.repository.student;

import com.clearance.gigster.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Boolean existsByRegNo(String regNo);

    Student findByRegNo(String regNo);
    Student findByUserId(Long id);

}
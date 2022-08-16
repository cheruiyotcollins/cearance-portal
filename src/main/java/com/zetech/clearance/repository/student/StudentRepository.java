package com.zetech.clearance.repository.student;

import com.zetech.clearance.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Boolean existsByRegNo(String regNo);

    Student findByRegNo(String regNo);
}
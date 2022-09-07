package com.zetech.clearance.repository.finance;

import com.zetech.clearance.model.finance.Finance;
import com.zetech.clearance.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface FinanceRepository extends JpaRepository<Finance, Long> {

    @Query("SELECT f FROM Finance f where f.student = :stude")
    Finance findByStudentId(Student stude);
}

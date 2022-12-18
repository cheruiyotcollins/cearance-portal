package com.clearance.gigster.repository.finance;

import com.clearance.gigster.model.finance.Finance;
import com.clearance.gigster.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FinanceRepository extends JpaRepository<Finance, Long> {

    @Query("SELECT f FROM Finance f where f.student = :stude")
    Finance findByStudentId(Student stude);
}

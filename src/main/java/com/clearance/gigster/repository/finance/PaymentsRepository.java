package com.clearance.gigster.repository.finance;

import com.clearance.gigster.model.finance.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository  extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long id);
}

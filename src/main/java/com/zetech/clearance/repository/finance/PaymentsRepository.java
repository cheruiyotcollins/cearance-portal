package com.zetech.clearance.repository.finance;

import com.zetech.clearance.model.finance.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository  extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long id);
}

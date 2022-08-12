package com.zetech.clearance.repository.finance;

import com.zetech.clearance.model.finance.Finance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FinanceRepository extends JpaRepository<Finance, Long> {
}

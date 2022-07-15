package com.example.pesaFix.repository;

import com.example.pesaFix.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan ,Long> {
}

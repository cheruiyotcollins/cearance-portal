package com.zetech.clearance.repository.records;

import com.zetech.clearance.model.records.Gown;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GownRepository extends JpaRepository<Gown,Long> {
    Gown findByStudentId(Long id);
}

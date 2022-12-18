package com.clearance.gigster.repository.records;

import com.clearance.gigster.model.records.Gown;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GownRepository extends JpaRepository<Gown,Long> {
    Gown findByStudentId(Long id);
}

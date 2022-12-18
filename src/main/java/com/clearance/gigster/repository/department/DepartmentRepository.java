package com.clearance.gigster.repository.department;

import com.clearance.gigster.model.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

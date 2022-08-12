package com.zetech.clearance.repository.department;

import com.zetech.clearance.model.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

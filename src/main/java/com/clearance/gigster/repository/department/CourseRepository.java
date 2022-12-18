package com.clearance.gigster.repository.department;

import com.clearance.gigster.model.department.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}

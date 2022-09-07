package com.zetech.clearance.repository.graduation;

import com.zetech.clearance.model.hod.GraduationList;
import com.zetech.clearance.model.library.Library;
import com.zetech.clearance.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GraduationListRepository  extends JpaRepository<GraduationList,Long> {
    @Query("SELECT g  FROM GraduationList g where g.student = :stude")
    GraduationList findByStudent(Student stude);

}

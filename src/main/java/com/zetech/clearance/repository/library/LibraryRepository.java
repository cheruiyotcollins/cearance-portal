package com.zetech.clearance.repository.library;

import com.zetech.clearance.model.finance.Finance;
import com.zetech.clearance.model.library.Library;
import com.zetech.clearance.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Query("SELECT l  FROM Library l where l.student = :stude")
    Library findByStudent(Student stude);
}

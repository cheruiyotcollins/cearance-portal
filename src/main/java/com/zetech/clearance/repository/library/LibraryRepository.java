package com.zetech.clearance.repository.library;

import com.zetech.clearance.model.library.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}

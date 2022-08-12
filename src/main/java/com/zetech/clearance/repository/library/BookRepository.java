package com.zetech.clearance.repository.library;

import com.zetech.clearance.model.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

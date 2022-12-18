package com.clearance.gigster.repository.library;

import com.clearance.gigster.model.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

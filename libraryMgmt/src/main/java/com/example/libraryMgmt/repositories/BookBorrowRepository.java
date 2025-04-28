package com.example.libraryMgmt.repositories;

import com.example.libraryMgmt.model.BookBorrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface BookBorrowRepository extends JpaRepository<BookBorrow, UUID>, JpaSpecificationExecutor<BookBorrow> {

    Optional<BookBorrow> findByUserIdAndBookId(String userId, UUID  bookId);
}

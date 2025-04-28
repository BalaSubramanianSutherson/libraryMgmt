package com.example.libraryMgmt.repositories;


import com.example.libraryMgmt.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, UUID> {
    boolean existsByNameIgnoreCase(String name);
    Optional<BookCategory> findById(UUID id);
}
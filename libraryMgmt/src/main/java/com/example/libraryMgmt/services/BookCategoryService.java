package com.example.libraryMgmt.services;

import com.example.libraryMgmt.dto.bookcategory.BookCategoryRequestDTO;
import com.example.libraryMgmt.dto.bookcategory.BookCategoryResponseDTO;
import com.example.libraryMgmt.model.BookCategory;
import com.example.libraryMgmt.repositories.BookCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class BookCategoryService {

    Logger logger = LoggerFactory.getLogger(BookCategoryService.class);
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    public BookCategoryService(){

    }

    public boolean isCategoryNameExists(String name) {
        return bookCategoryRepository.existsByNameIgnoreCase(name);
    }

    public BookCategoryResponseDTO createCategory(BookCategoryRequestDTO request) {
        if (isCategoryNameExists(request.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        logger.info("Creating new category with name: {}", request.getName());


        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logger.info("Logged in user ID: {}", userId);

        BookCategory category = new BookCategory();
        category.setId(UUID.randomUUID());
        category.setName(request.getName());
        category.setActive(true);
        category.setCreatedBy(userId);
        category.setUpdatedBy(userId);

        BookCategory bookCategory = bookCategoryRepository.save(category);
        logger.info("Category created successfully with ID: {}", bookCategory.getId());
        return BookCategoryResponseDTO.toDTO(bookCategory);

    }

}

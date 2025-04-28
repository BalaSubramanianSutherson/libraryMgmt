package com.example.libraryMgmt.controllers;


import com.example.libraryMgmt.dto.ApiResponse;
import com.example.libraryMgmt.dto.bookcategory.BookCategoryRequestDTO;
import com.example.libraryMgmt.dto.bookcategory.BookCategoryResponseDTO;
import com.example.libraryMgmt.services.BookCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    Logger logger = LoggerFactory.getLogger(BookCategoryController.class);

    @PostMapping
    public ResponseEntity<ApiResponse<BookCategoryResponseDTO>> createCategory(@RequestBody BookCategoryRequestDTO request) {
        logger.info("Received request to create category: {}", request.getName());
        BookCategoryResponseDTO response = bookCategoryService.createCategory(request);
        return  ResponseEntity.ok(new ApiResponse<>(
                200,
                true,
                "Category fetched successfully",
                response)
        );
    }
}

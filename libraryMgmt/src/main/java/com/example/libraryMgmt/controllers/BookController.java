package com.example.libraryMgmt.controllers;


import com.example.libraryMgmt.dto.ApiResponse;
import com.example.libraryMgmt.dto.PagedResponse;
import com.example.libraryMgmt.dto.book.BookRequestDTO;
import com.example.libraryMgmt.dto.book.BookResponseDTO;
import com.example.libraryMgmt.dto.bookborrow.BookBorrowFilterDTO;
import com.example.libraryMgmt.dto.bookborrow.BookBorrowRequestDTO;
import com.example.libraryMgmt.dto.bookborrow.BookBorrowResponseDTO;
import com.example.libraryMgmt.dto.bookcategory.BookCategoryFilterDTO;
import com.example.libraryMgmt.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponseDTO>> createBook(@RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Book added", bookService.createBook(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> updateBook(@PathVariable UUID id, @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Book updated", bookService.updateBook(id, dto)));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Book deleted", null));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse>> searchBooks(@RequestBody BookCategoryFilterDTO dto){
        Page<BookResponseDTO> result = bookService.searchBooks(dto);
        PagedResponse pageResult = new PagedResponse(result.getContent(), result.getTotalElements());
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Books fetched successfully", pageResult));
    }

    @PostMapping("/borrow")
    public ResponseEntity<ApiResponse<BookBorrowResponseDTO>> borrowBook(@RequestBody BookBorrowRequestDTO request) {
        BookBorrowResponseDTO result = bookService.borrowBook(request.getUserId(), request.getBookId());
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Book check-out successfully", result));
    }

    @PostMapping("/return")
    public ResponseEntity<ApiResponse<String>> returnBook(@RequestBody BookBorrowRequestDTO request) {
        bookService.returnBook(request.getUserId(), request.getBookId());
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Book check-in successfully", null));
    }

    @PostMapping("/borrow/history")
    public ResponseEntity<ApiResponse<PagedResponse>> borrowHistory(@RequestBody BookBorrowFilterDTO request) {
        Page<BookBorrowResponseDTO> result = bookService.searchBorrowBooks(request);

        PagedResponse pageResult = new PagedResponse(result.getContent(), result.getTotalElements());
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Book borrow history fetched successfully", pageResult));
    }
}

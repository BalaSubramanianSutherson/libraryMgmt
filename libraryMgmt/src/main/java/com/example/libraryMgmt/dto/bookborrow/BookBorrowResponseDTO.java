package com.example.libraryMgmt.dto.bookborrow;

import com.example.libraryMgmt.model.BookBorrow;

import java.time.LocalDate;
import java.time.ZoneId;

public class BookBorrowResponseDTO {
    private String bookId;
    private String userId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public BookBorrowResponseDTO() {
    }


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public static BookBorrowResponseDTO toDTO(BookBorrow entity) {
        BookBorrowResponseDTO dto = new BookBorrowResponseDTO();
        dto.setBookId(entity.getBook().getId().toString());
        dto.setUserId(entity.getUser().getId());
        dto.setBorrowDate(entity.getCheckOutAt().atZone(ZoneId.systemDefault()).toLocalDate());
        dto.setDueDate(entity.getDueDate().atZone(ZoneId.systemDefault()).toLocalDate());
        dto.setStatus(entity.getStatus().name());
        if (entity.getCheckInAt() != null) {
            dto.setReturnedDate(entity.getCheckInAt().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        return dto;
    }
}

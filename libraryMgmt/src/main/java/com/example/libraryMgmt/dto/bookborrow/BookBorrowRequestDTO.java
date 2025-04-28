package com.example.libraryMgmt.dto.bookborrow;

public class BookBorrowRequestDTO {
    private String bookId;
    private String userId;

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
}

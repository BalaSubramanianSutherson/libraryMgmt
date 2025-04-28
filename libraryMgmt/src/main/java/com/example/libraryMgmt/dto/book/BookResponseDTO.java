package com.example.libraryMgmt.dto.book;

import com.example.libraryMgmt.model.Book;

import java.time.Instant;
import java.util.UUID;

public class BookResponseDTO {
    private UUID id;
    private String name;
    private String author;
    private Instant publishedDate;
    private String categoryName;
    private boolean isAvailable;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Instant publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static BookResponseDTO toDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setCategoryName(book.getCategory().getName());
        dto.setAvailable(book.getAvailable());
        return dto;
    }
}

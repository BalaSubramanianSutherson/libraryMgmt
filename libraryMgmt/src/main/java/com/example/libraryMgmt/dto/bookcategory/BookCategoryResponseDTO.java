package com.example.libraryMgmt.dto.bookcategory;

import com.example.libraryMgmt.model.BookCategory;

import java.time.Instant;
import java.util.UUID;

public class BookCategoryResponseDTO {
    private UUID id;
    private String name;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    public BookCategoryResponseDTO() {
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static BookCategoryResponseDTO toDTO(BookCategory entity) {
        BookCategoryResponseDTO dto = new BookCategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}

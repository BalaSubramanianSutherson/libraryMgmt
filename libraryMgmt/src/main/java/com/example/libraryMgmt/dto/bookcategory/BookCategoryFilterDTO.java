package com.example.libraryMgmt.dto.bookcategory;

import java.time.LocalDate;

public class BookCategoryFilterDTO {
    private String name = "";
    private String author = "";
    private LocalDate publishedFrom;
    private LocalDate publishedTo;
    private LocalDate createdFrom;
    private LocalDate createdTo;
    private int pageNumber = 0;
    private int pageSize = 10;
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

    public LocalDate getPublishedFrom() {
        return publishedFrom;
    }

    public void setPublishedFrom(LocalDate publishedFrom) {
        this.publishedFrom = publishedFrom;
    }

    public LocalDate getPublishedTo() {
        return publishedTo;
    }

    public void setPublishedTo(LocalDate publishedTo) {
        this.publishedTo = publishedTo;
    }

    public LocalDate getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(LocalDate createdFrom) {
        this.createdFrom = createdFrom;
    }

    public LocalDate getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(LocalDate createdTo) {
        this.createdTo = createdTo;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

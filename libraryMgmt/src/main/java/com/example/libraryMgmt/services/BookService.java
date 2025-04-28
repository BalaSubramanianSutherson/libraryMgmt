package com.example.libraryMgmt.services;


import com.example.libraryMgmt.dto.book.BookRequestDTO;
import com.example.libraryMgmt.dto.book.BookResponseDTO;
import com.example.libraryMgmt.dto.bookborrow.BookBorrowFilterDTO;
import com.example.libraryMgmt.dto.bookborrow.BookBorrowResponseDTO;
import com.example.libraryMgmt.dto.bookcategory.BookCategoryFilterDTO;
import com.example.libraryMgmt.model.*;
import com.example.libraryMgmt.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookBorrowRepository bookBorrowRepository;

    @Autowired
    private UserRepository userRepository;

    public String getLoggedInUserId() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logger.info("Logged in user ID: {}", userId);
        return userId;
    }
    public BookResponseDTO createBook(BookRequestDTO dto) {
        logger.info("Creating new book with name: {}", dto.getName());
        if (dto.getName().isEmpty() || dto.getAuthor().isEmpty() || dto.getCategoryId() == null ||dto.getPublishedDate() == null ) {
            throw new IllegalArgumentException("Mandatory fields cannot be empty");
        }

        BookCategory category = validateBookCategory(dto.getCategoryId());

        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        book.setPublishedDate(dto.getPublishedDate());
        book.setCategory(category);
        book.setAvailable(dto.getAvailable());
        book.setCreatedBy(getLoggedInUserId());
        book.setUpdatedBy(getLoggedInUserId());

        Book updated = bookRepository.save(book);

        return BookResponseDTO.toDTO(updated);
    }

    public BookResponseDTO updateBook(UUID id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

       if(!dto.getName().isEmpty()){
           book.setName(dto.getName());
        }
        if(!dto.getAuthor().isEmpty()){
            book.setAuthor(dto.getAuthor());
        }
        if(dto.getPublishedDate() != null){
            book.setPublishedDate(dto.getPublishedDate());
        }
        if(dto.getCategoryId() != null){
            BookCategory category = validateBookCategory(dto.getCategoryId());
            book.setCategory(category);
        }
        book.setAvailable(dto.getAvailable());
        book.setUpdatedBy(getLoggedInUserId());
        book.setUpdatedAt(Instant.now());

        Book updated = bookRepository.save(book);

        return BookResponseDTO.toDTO(updated);
    }

    private BookCategory validateBookCategory(UUID categoryId) {
        BookCategory category = bookCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getActive()) {
            throw new RuntimeException("Category is not active");
        }
        return category;
    }

    public void deleteBook(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (!book.getAvailable()) {
            throw new IllegalArgumentException("Book is not available in library");
        }
        book.setActive(false);
        book.setUpdatedBy("LoggedInUser");
        book.setUpdatedAt(Instant.now());
        bookRepository.save(book);
    }

    public Page<BookResponseDTO> searchBooks(BookCategoryFilterDTO dto) {
        logger.info("Searching books with filter: {}", dto);
        Pageable pageable = PageRequest.of(dto.getPageNumber(), dto.getPageSize());

        Specification<Book> spec = BookSpecification.filterBy(
                dto.getName(),
                dto.getAuthor(),
                dto.getPublishedFrom(),
                dto.getPublishedTo(),
                dto.getCreatedFrom(),
                dto.getCreatedTo()
                );
        return bookRepository.findAll(spec, pageable).map(BookResponseDTO::toDTO);
    }
    private Book validateBook(String bookId){
        Book book = bookRepository.findById(UUID.fromString(bookId))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if (!book.getAvailable() || !book.getActive()) {
            throw new IllegalArgumentException("Book is not available");
        }
        return book;
    }

    public BookBorrowResponseDTO borrowBook(String userId, String bookId) {
        logger.info("Borrowing book with ID: {}", bookId);
        String uid ="";
        if(userId.isEmpty()){
            uid = getLoggedInUserId();
        }else{
            uid =  userId;
        }
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Book book = validateBook(bookId);

        BookBorrowFilterDTO request = new BookBorrowFilterDTO();
        request.setUserId(user.getId());
        request.setStatus(BorrowStatus.BORROWED);

        Specification<BookBorrow> spec = BookBorrowSpecification.filterBy(request);

        List<BookBorrow> bookBorrow = bookBorrowRepository.findAll(spec);
        logger.info("Book borrow size: {}", bookBorrow.size());
        if (bookBorrow.size() >= 2 ){
            throw new IllegalArgumentException("User already borrow maximum number of books");
        }

        BookBorrow borrow = new BookBorrow();
        borrow.setId(UUID.randomUUID());
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setCheckOutAt(Instant.now());
        borrow.setDueDate(Instant.now().plusSeconds(604800)); // 7 days
        borrow.setStatus(BorrowStatus.BORROWED.name());
        borrow.setCreatedBy(getLoggedInUserId());
        borrow.setUpdatedBy(getLoggedInUserId());
        BookBorrow bookBorrowResult = bookBorrowRepository.save(borrow);

        book.setAvailable(false);
        book.setUpdatedBy(getLoggedInUserId());
        book.setUpdatedAt(Instant.now());
        bookRepository.save(book);
        return BookBorrowResponseDTO.toDTO(bookBorrowResult);
    }

    public void returnBook(String userId, String bookId) {
        logger.info("Returning book with ID: {}", bookId);
        String uid ="";
        if(userId.isEmpty()){
            uid = getLoggedInUserId();
        }else{
            uid =  userId;
        }
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Book book = bookRepository.findById(UUID.fromString(bookId))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        logger.info("Book borrow size: {}", book.getId());

        BookBorrowFilterDTO request = new BookBorrowFilterDTO();
        request.setUserId(user.getId());
        request.setBookId(book.getId());
        request.setStatus(BorrowStatus.BORROWED);

        Specification<BookBorrow> spec = BookBorrowSpecification.filterBy(request);

        List<BookBorrow> bookBorrows = bookBorrowRepository.findAll(spec);
        if (bookBorrows.size() != 1) {
            throw new IllegalArgumentException("Borrow record not found.");
        }
        logger.info("Book borrow size: {}", bookBorrows.getFirst().getId());
        BookBorrow bookBorrow = bookBorrows.getFirst();
        bookBorrow.setCheckInAt(Instant.now());
        bookBorrow.setStatus(BorrowStatus.RETURNED.name());
        bookBorrow.setUpdatedBy(getLoggedInUserId());
        bookBorrow.setUpdatedAt(Instant.now());
        bookBorrowRepository.save(bookBorrow);

        book.setAvailable(true);
        book.setUpdatedBy(getLoggedInUserId());
        book.setUpdatedAt(Instant.now());
        bookRepository.save(book);
        logger.info("Book returned successfully with ID: {}", book.getId());
    }

    public Page<BookBorrowResponseDTO> searchBorrowBooks(BookBorrowFilterDTO request){
        logger.info("Searching borrowed books with filter: {}", request);
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());

        Specification<BookBorrow> spec = BookBorrowSpecification.filterBy(request);

        return bookBorrowRepository.findAll(spec, pageable).map(BookBorrowResponseDTO::toDTO);

    }
}

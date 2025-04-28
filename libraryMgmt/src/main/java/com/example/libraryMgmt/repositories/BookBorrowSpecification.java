package com.example.libraryMgmt.repositories;

import com.example.libraryMgmt.dto.bookborrow.BookBorrowFilterDTO;
import com.example.libraryMgmt.model.BookBorrow;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookBorrowSpecification {

    public static Specification<BookBorrow> filterBy(BookBorrowFilterDTO request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getUserId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), request.getUserId()));
            }

            if (request.getBookId() != null) {
                predicates.add(cb.equal(root.get("book").get("id"), request.getBookId()));
            }

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus().name()));
            }

            if (Boolean.TRUE.equals(request.getOverdue())) {
                predicates.add(cb.and(
                        cb.isNull(root.get("checkInAt")),
                        cb.lessThan(root.get("dueDate"), LocalDate.now())
                ));
            }

            if (request.getCreatedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), request.getCreatedFrom().atStartOfDay()));
            }

            if (request.getCreatedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), request.getCreatedTo().atTime(LocalTime.MAX)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

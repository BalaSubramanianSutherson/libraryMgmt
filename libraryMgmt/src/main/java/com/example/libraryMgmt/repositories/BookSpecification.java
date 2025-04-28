package com.example.libraryMgmt.repositories;

import com.example.libraryMgmt.model.Book;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> filterBy(
            String name,
            String author,
            LocalDate publishedFrom,
            LocalDate publishedTo,
            LocalDate createdFrom,
            LocalDate createdTo
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (author != null && !author.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }

            if (publishedFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishedDate"), publishedFrom.atStartOfDay()));
            }

            if (publishedTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publishedDate"), publishedTo.atTime(LocalTime.MAX)));
            }

            if (createdFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdFrom.atStartOfDay()));
            }

            if (createdTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdTo.atTime(LocalTime.MAX)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


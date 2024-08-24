package com.example.books.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  Iterable<Book> findByAuthors(Author author);

  Iterable<Book> findByCategories(Category category);

}

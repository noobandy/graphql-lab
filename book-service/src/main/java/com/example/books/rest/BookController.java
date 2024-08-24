package com.example.books.rest;

import com.example.books.dto.BookInput;
import com.example.books.model.AuthorRepository;
import com.example.books.model.Book;
import com.example.books.model.BookRepository;
import com.example.books.model.CategoryRepository;
import com.example.books.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private BookService bookService;



  @GetMapping("/books")
  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  @PostMapping("/books")
  public ResponseEntity<Book> addBook(@RequestBody BookInput bookInput) {
    Book book = bookService.addBook(bookInput);
    return ResponseEntity.ok(book);
  }
  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getById(Long id) {
    return ResponseEntity.of(bookRepository.findById(id));
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<Book> updateById(Long id, @RequestBody BookInput bookInput) {
    Book book = bookService.updateBook(id, bookInput);
    return ResponseEntity.ok(book);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Void> deleteById(Long id) {

    Optional<Book> found = bookRepository.findById(id);
    if(found.isPresent()) {
      Book book = found.get();
      book.setDeleted(Boolean.TRUE);
      bookRepository.save(book);
    }
    return ResponseEntity.noContent().build();
  }

}

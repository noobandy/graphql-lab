package com.example.books.rest;

import com.example.books.dto.AuthorInput;
import com.example.books.model.Book;
import com.example.books.model.BookRepository;
import com.example.books.model.Author;
import com.example.books.model.AuthorRepository;
import com.example.books.service.AuthorService;
import java.util.ArrayList;
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
public class AuthorController {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private AuthorService authorService;

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/authors")
  public List<Author> getAll() {
    return authorRepository.findAll();
  }

  @PostMapping("/authors")
  public ResponseEntity<Author> addAuthor(@RequestBody AuthorInput authorInput) {
    Author author = authorService.addAuthor(authorInput);
    return ResponseEntity.ok(author);
  }

  @GetMapping("/authors/{id}")
  public ResponseEntity<Author> getById(Long id) {
    return ResponseEntity.of(authorRepository.findById(id));
  }

  @GetMapping("/authors/{id}/books")
  public ResponseEntity<List<Book>> getBooksByAuthor(Long id) {
    List<Book> books = new ArrayList<>();
    Optional<Author> author = authorRepository.findById(id);
    if(author.isPresent()) {
      bookRepository.findByAuthors(author.get()).forEach(books::add);
    }

    return ResponseEntity.ok(books);
  }

  @PutMapping("/authors/{id}")
  public ResponseEntity<Author> updateById(Long id, @RequestBody AuthorInput authorInput) {
    Author author = authorService.updateAuthor(id, authorInput);
    return ResponseEntity.ok(author);
  }

  @DeleteMapping("/authors/{id}")
  public ResponseEntity<Void> deleteById(Long id) {

    Optional<Author> found = authorRepository.findById(id);
    if(found.isPresent()) {
      Author author = found.get();
      author.setDeleted(Boolean.TRUE);
      authorRepository.save(author);
    }
    return ResponseEntity.noContent().build();
  }

}

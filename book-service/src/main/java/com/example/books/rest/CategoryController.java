package com.example.books.rest;

import com.example.books.dto.CategoryInput;
import com.example.books.model.Book;
import com.example.books.model.BookRepository;
import com.example.books.model.Category;
import com.example.books.model.CategoryRepository;
import com.example.books.service.CategoryService;
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
public class CategoryController {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/categories")
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  @PostMapping("/categories")
  public ResponseEntity<Category> addCategory(@RequestBody CategoryInput categoryInput) {
    Category cat = categoryService.addCategory(categoryInput);
    return ResponseEntity.ok(cat);
  }

  @GetMapping("/categories/{id}")
  public ResponseEntity<Category> getById(Long id) {
    return ResponseEntity.of(categoryRepository.findById(id));
  }

  @GetMapping("/categories/{id}/books")
  public ResponseEntity<List<Book>> getBooksByCategory(Long id) {
    List<Book> books = new ArrayList<>();
    Optional<Category> category = categoryRepository.findById(id);
    if(category.isPresent()) {
      bookRepository.findByCategories(category.get()).forEach(books::add);
    }

    return ResponseEntity.ok(books);
  }

  @PutMapping("/categories/{id}")
  public ResponseEntity<Category> updateById(Long id, @RequestBody CategoryInput categoryInput) {
    Category cat = categoryService.updateCategory(id, categoryInput);
    return ResponseEntity.ok(cat);
  }

  @DeleteMapping("/categories/{id}")
  public ResponseEntity<Void> deleteById(Long id) {

    Optional<Category> found = categoryRepository.findById(id);
    if(found.isPresent()) {
      Category cat = found.get();
      cat.setDeleted(Boolean.TRUE);
      categoryRepository.save(cat);
    }
    return ResponseEntity.noContent().build();
  }

}

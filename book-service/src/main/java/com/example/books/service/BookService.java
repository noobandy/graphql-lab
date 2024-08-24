package com.example.books.service;

import com.example.books.dto.BookInput;
import com.example.books.exception.BadInputException;
import com.example.books.model.Author;
import com.example.books.model.AuthorRepository;
import com.example.books.model.Book;
import com.example.books.model.BookRepository;
import com.example.books.model.Category;
import com.example.books.model.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BookService {


  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
      CategoryRepository categoryRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.categoryRepository = categoryRepository;
  }

  public Book addBook(BookInput bookInput) {

    if(bookInput.title() == null || bookInput.title().isBlank()) {
      throw new BadInputException("Missing title");
    }

    if(bookInput.desc() == null || bookInput.desc().isBlank()) {
      throw new BadInputException("Missing description");
    }

    if(CollectionUtils.isEmpty(bookInput.authorIds())) {
      throw new BadInputException("At least one author is required");
    }

    if(CollectionUtils.isEmpty(bookInput.categoryIds())) {
      throw new BadInputException("At least one category is required");
    }
    List<Author> authors = authorRepository.findAllById(bookInput.authorIds());

    if(CollectionUtils.isEmpty(authors)) {
      throw new BadInputException("Invalid authors");
    }

    List<Category> categories = categoryRepository.findAllById(bookInput.categoryIds());

    if(CollectionUtils.isEmpty(categories)) {
      throw new BadInputException("Invalid categories");
    }

    Book b = new Book();
    b.setTitle(bookInput.title().trim());
    b.setDesc(bookInput.desc().trim());
    b.setAuthors(authors);
    b.setCategories(categories);
    b.setDeleted(Boolean.FALSE);

    bookRepository.save(b);
    return b;
  }



  public Book updateBook(Long id, BookInput bookInput) {
    Optional<Book> found = bookRepository.findById(id);

    if(found.isEmpty()) {
      throw new BadInputException("Book not found");
    }

    if(bookInput.title() != null && bookInput.title().isBlank()) {
      throw new BadInputException("Missing title");
    }

    if(bookInput.desc() != null && bookInput.desc().isBlank()) {
      throw new BadInputException("Missing description");
    }

    List<Author> authors = null;
    if(!CollectionUtils.isEmpty(bookInput.authorIds())) {
      authors = authorRepository.findAllById(bookInput.authorIds());
      if(CollectionUtils.isEmpty(authors)) {
        throw new BadInputException("Invalid authors");
      }
    }

    List<Category> categories = null;
    if(!CollectionUtils.isEmpty(bookInput.categoryIds())) {
      categories = categoryRepository.findAllById(bookInput.categoryIds());
      if(CollectionUtils.isEmpty(categories)) {
        throw new BadInputException("Invalid categories");
      }
    }
    Book b = found.get();
    if(bookInput.title() != null) {
      b.setTitle(bookInput.title().trim());
    }

    if(bookInput.desc() != null) {
      b.setDesc(bookInput.desc().trim());
    }

    if(authors != null) {
      b.setAuthors(authors);
    }

    if(categories != null) {
      b.setCategories(categories);
    }
    bookRepository.save(b);
    return b;
  }
}

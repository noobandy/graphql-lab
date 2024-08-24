package com.example.books.graphql;

import com.example.books.model.Author;
import com.example.books.model.AuthorRepository;
import com.example.books.model.Book;
import com.example.books.model.BookRepository;
import com.example.books.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphqlController {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private AuthorRepository authorRepository;

  @QueryMapping
  public Optional<Book> bookById(@Argument Long id) {
    return bookRepository.findById(id);
  }

  @SchemaMapping
  public List<Author> authors(Book book) {
    return book.getAuthors();
  }

  @SchemaMapping
  public List<Category> categories(Book book) {
    return book.getCategories();
  }
}

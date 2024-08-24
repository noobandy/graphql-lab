package com.example.books.service;

import com.example.books.dto.AuthorInput;
import com.example.books.exception.BadInputException;
import com.example.books.model.Author;
import com.example.books.model.AuthorRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Author addAuthor(AuthorInput authorInput) {
    if(authorInput.name() == null || authorInput.name().isBlank()) {
      throw new BadInputException("Author name can't be empty");
    }

    if(authorInput.about() == null || authorInput.about().isBlank()) {
      throw new BadInputException("Author about can't be empty");
    }

    Author author = new Author();
    author.setName(authorInput.name().trim());
    author.setAbout(authorInput.about().trim());
    author.setDeleted(Boolean.FALSE);

    authorRepository.save(author);

    return author;

  }

  public  Author updateAuthor(Long id, AuthorInput authorInput) {

    if(authorInput.name() != null && authorInput.name().isBlank()) {
      throw new BadInputException("Author name can't be empty");
    }

    if(authorInput.about() != null && authorInput.about().isBlank()) {
      throw new BadInputException("Author about can't be empty");
    }


    Optional<Author> found = authorRepository.findById(id);
    if(found.isEmpty()) {
      throw new BadInputException("Author not found");
    }

    Author author = found.get();
    if(authorInput.name() != null) {
      author.setName(authorInput.name().trim());
    }
    if(authorInput.about() != null) {
      author.setAbout(authorInput.about().trim());
    }

    authorRepository.save(author);

    return author;
  }

}

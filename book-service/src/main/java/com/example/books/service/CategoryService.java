package com.example.books.service;

import com.example.books.dto.CategoryInput;
import com.example.books.exception.BadInputException;
import com.example.books.model.Category;
import com.example.books.model.CategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category addCategory(CategoryInput categoryInput) {
    if(categoryInput.name() == null || categoryInput.name().isBlank()) {
      throw new BadInputException("Category name can't be empty");
    }

    if(categoryInput.desc() == null || categoryInput.desc().isBlank()) {
      throw new BadInputException("Category description can't be empty");
    }

    Optional<Category> found = categoryRepository.findByName(categoryInput.name().trim());
    if(found.isPresent()) {
      throw new BadInputException("Category with given name already exists");
    }

    Category category = new Category();
    category.setName(categoryInput.name().trim());
    category.setDesc(categoryInput.desc().trim());
    category.setDeleted(Boolean.FALSE);

    categoryRepository.save(category);

    return category;

  }

  public  Category updateCategory(Long id, CategoryInput categoryInput) {

    if(categoryInput.name() != null && categoryInput.name().isBlank()) {
      throw new BadInputException("Category name can't be empty");
    }

    if(categoryInput.desc() != null && categoryInput.desc().isBlank()) {
      throw new BadInputException("Category description can't be empty");
    }

    Optional<Category> found = categoryRepository.findById(id);
    if(found.isEmpty()) {
      throw new BadInputException("Category not found");
    }


    if(categoryInput.name() != null) {
      Optional<Category> foundByName = categoryRepository.findByName(categoryInput.name().trim());
      if(foundByName.isPresent() && foundByName.get().getId().longValue() != id.longValue()) {
        throw new BadInputException("Another Category with given name already exists");
      }
    }

    Category category = found.get();
    if(categoryInput.name() != null) {
      category.setName(categoryInput.name().trim());
    }
    if(categoryInput.desc() != null) {
      category.setDesc(categoryInput.desc().trim());
    }

    categoryRepository.save(category);

    return category;
  }

}

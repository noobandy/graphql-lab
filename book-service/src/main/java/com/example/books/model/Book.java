package com.example.books.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLRestriction("deleted = false")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;

  private String desc;

  @OneToMany(targetEntity = BookMetaData.class, mappedBy = "book", fetch = FetchType.EAGER)
  private List<BookMetaData> metaData;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Category> categories;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Author> authors;

  private boolean deleted = Boolean.FALSE;

  public Book() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<BookMetaData> getMetaData() {
    return metaData;
  }

  public void setMetaData(List<BookMetaData> metaData) {
    this.metaData = metaData;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}

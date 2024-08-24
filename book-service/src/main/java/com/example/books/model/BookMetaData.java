package com.example.books.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BookMetaData {

  @Id
  private Long id;

  @ManyToOne
  private Book book;
  private String attributeName;

  private String getAttributeValue;


}

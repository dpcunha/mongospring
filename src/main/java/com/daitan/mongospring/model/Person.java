package com.daitan.mongospring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
public class Person {

  @Id
  private String id;

  private String name;
  private String country;

  Person(){}

  public Person(String name, String country) {
    this.name = name;
    this.country = country;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "Person {\n" +
        " id=" + id +
        ",\n name='" + name + '\'' +
        ",\n country='" + country + '\'' +
        "\n}";
  }
}

package com.daitan.mongospring.model;

public class DTO {
  private String id;
  private String name;
  private String country;
  private String status;

  public DTO(String id, String name, String country, boolean exists) {
    this.id = id;
    this.name = name;
    this.country = country;
    if(exists)
      setStatus("User already there!");
    else
      setStatus("Created Successfully!");
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

  public String getStatus() {
    return status;
  }

  private void setStatus(String status) {
    this.status = status;
  }
}

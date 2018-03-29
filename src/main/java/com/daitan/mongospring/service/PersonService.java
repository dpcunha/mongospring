package com.daitan.mongospring.service;

import com.daitan.mongospring.model.Person;

import java.util.List;

public interface PersonService {
  Person findByName(String name);
  Person save(String name, String country);
  Person update(String oldId, Person a);
  Person findPerson(Person n);
  Person findById(String id);
  List<Person> findByCountry(String country);
  List<Person> findAllUsers();
  void delete(Person a);
  void deleteAllUsers();
  boolean userExists(Person b);

}

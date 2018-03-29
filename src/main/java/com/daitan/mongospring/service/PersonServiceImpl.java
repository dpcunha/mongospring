package com.daitan.mongospring.service;

import com.daitan.mongospring.model.Person;
import com.daitan.mongospring.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

  @Autowired
  PersonRepository personRepository;

  @Override
  public Person findByName(String name) {
    for(Person p : personRepository.findAll()) {
      if(p.getName().equalsIgnoreCase(name)){
        return p;
      }
    }
    return null;
  }

  public Person findById (String id) {
    List<Person> dbStatusPersons = personRepository.findAll();
    for (Person p : dbStatusPersons) {
      if (p.getId().equals(id)) {
        return p;
      }
    }
    return null;
  }

  @Override
  public List<Person> findByCountry(String country) {
    List<Person> ansList = new ArrayList<>();
    for(Person p : personRepository.findAll()){
      if(p.getCountry().equalsIgnoreCase(country)){
        ansList.add(p);
      }
    }
    return ansList;
  }

  @Override
  public Person save(String name, String country) {
    Person n = new Person(name, country);
    personRepository.save(n); // At this moment an Id should be provided by auto-generation.
    return n; // Person with all fields populated
  }

  @Override
  public Person update(String oldId, Person newPerson) {
    Person localPerson = findById(oldId); // localPerson owns the ID of the person found in DB
    localPerson.setName(newPerson.getName() + " (*)");
    localPerson.setCountry(newPerson.getCountry()+ " (*)");
    //since id matches another one within the DB, overwrite data!
    return personRepository.save(localPerson);
  }

  @Override
  public List<Person> findAllUsers() {
    return personRepository.findAll();
  }

  @Override
  public void delete(Person a) {
    personRepository.delete(findPerson(a));
  }

  @Override
  public void deleteAllUsers() {
    personRepository.deleteAll();
  }

  @Override
  public boolean userExists(Person b) {
    return findPerson(b) != null;
  }

  @Override
  public Person findPerson(Person n) {
    List<Person> dbStatusPersons = personRepository.findAll();
    for (Person p : dbStatusPersons) {
      if (p.getName().equalsIgnoreCase(n.getName()) &&
          p.getCountry().equalsIgnoreCase(n.getCountry())) {
        return p;
      }
    }
    return null;
  }
}

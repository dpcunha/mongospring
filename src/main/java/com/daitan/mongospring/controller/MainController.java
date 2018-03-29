package com.daitan.mongospring.controller;

import com.daitan.mongospring.model.DTO;
import com.daitan.mongospring.model.Person;
import com.daitan.mongospring.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/")
public class MainController {

  @Autowired
  PersonService personService;

  @PostMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
  public ResponseEntity<DTO> addNewUser(@RequestBody Person person) {

    if (personService.userExists(person)) {
      return new ResponseEntity<>(
          new DTO(person.getId(), person.getName(), person.getCountry(), true),
          HttpStatus.CONFLICT);
    }

    Person personAdded = personService.save(person.getName(), person.getCountry());
    return new ResponseEntity<>(
        new DTO(personAdded.getId(), personAdded.getName(), personAdded.getCountry(), false),
        HttpStatus.CREATED);
  }

  @GetMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  // Map ONLY GET Requests
  public ResponseEntity<DTO> getUser(@PathVariable("id") String id) {
    // This returns a JSON or XML with the users
    Person aPerson = personService.findById(id);
    if (aPerson == null) {
      return new ResponseEntity<>(new DTO("", "", "", false), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(
        new DTO(aPerson.getId(), aPerson.getName(), aPerson.getCountry(), false), HttpStatus.OK);
  }

  @GetMapping(value = "user/name", produces = MediaType.APPLICATION_JSON_VALUE)
  // Map ONLY GET Requests
  public ResponseEntity<DTO> findByName(@RequestParam("name") String name) {
    // This returns a JSON or XML with the users
    Person aPerson = personService.findByName(name);
    if (aPerson == null) {
      return new ResponseEntity<>(new DTO("", "", "", false), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(
        new DTO(aPerson.getId(), aPerson.getName(), aPerson.getCountry(), false), HttpStatus.OK);
  }

  @GetMapping(value = "user/country", produces = MediaType.APPLICATION_JSON_VALUE)
  // Map ONLY GET Requests
  public ResponseEntity<List<Person>> findByCountry(@RequestParam("country") String country) {
    // This returns a JSON or XML with the users
    List<Person> personnel = personService.findByCountry(country);
    if (personnel == null || personnel.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(personnel, HttpStatus.OK);
  }

  @PutMapping(path = "user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  // Map ONLY POST Requests
  public ResponseEntity<String> updateUser(@PathVariable("id") String id,
      @RequestBody @NotBlank(message = "Person must have Name & Country") Person person) {

    Person personToFetch = personService.findById(id);

    if (personToFetch == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Person temp = personService.update(id, person);
    return new ResponseEntity<>(temp.toString() + " Person " + id + " updated!", HttpStatus.OK);
  }

  @DeleteMapping(value = "user/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
    Person personToFetch = personService.findById(id);
    if (personToFetch == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    personService.delete(personToFetch);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "user")
  public ResponseEntity<Void> deleteAllUsers() {
    List<Person> personToFetch = personService.findAllUsers();
    if (personToFetch.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.ACCEPTED);// No changes
    }

    personService.deleteAllUsers();
    return new ResponseEntity<>(HttpStatus.OK);
  }


  @GetMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY GET Requests
  public ResponseEntity<List<Person>> getAllUsers() {
    List<Person> users = personService.findAllUsers();
    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Empty body
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }
}

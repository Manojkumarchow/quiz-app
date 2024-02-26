package com.example.dormnestapp.controllers;

import com.example.dormnestapp.model.Person;
import com.example.dormnestapp.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
@CrossOrigin(origins = "${frontend.url}")
@SuppressWarnings("unused")
public class PersonController {

    Logger personControllerLogger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/getAllPersons")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            personControllerLogger.info("In the GET ALL PERSONS METHOD");
            List<Person> persons = personService.getAllPersons();
            personControllerLogger.info("Persons size : {}", persons);
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

//    @Cacheable(value = "persons", key = "#id", unless = "#result.id > 10")
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable long id) {
        personControllerLogger.info("In the getPersonById METHOD");
        personControllerLogger.info("GOING TO SERVICE with ID {}", id);
        return personService.getPersonById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

}

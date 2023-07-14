package com.example.dormnestapp.services;

import com.example.dormnestapp.dao.PersonDao;
import com.example.dormnestapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class PersonService {

    @Autowired
    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> getAllPersons() {
        try {
            return personDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Person getPersonById(long id) {
        try {
            return personDao.findById(id).orElse(null);
//            return personDao.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
//            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}

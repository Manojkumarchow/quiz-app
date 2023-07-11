package com.example.dormnestapp.services;

import com.example.dormnestapp.dao.PersonDao;
import com.example.dormnestapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return personDao.findAll();

    }


}

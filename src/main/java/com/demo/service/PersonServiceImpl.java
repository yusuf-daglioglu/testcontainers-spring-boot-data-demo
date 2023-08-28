package com.demo.service;

import com.demo.domain.Person;
import com.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> loadAll() {
        return personRepository.findAll();
    }

    @Override
    public void add(String firstName, int nationalId) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setNationalId(nationalId);
        personRepository.save(person);
    }
}

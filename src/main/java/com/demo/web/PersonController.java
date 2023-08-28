package com.demo.web;

import com.demo.domain.Person;
import com.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/add")
    public void add() {
        personService.add("Jack", 1003);
    }

    @GetMapping("/all")
    public List<Person> all() {
        return personService.loadAll();
    }
}

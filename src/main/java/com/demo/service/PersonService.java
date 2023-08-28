package com.demo.service;

import com.demo.domain.Person;

import java.util.List;

public interface PersonService {

    List<Person> loadAll();

    public void add(String firstName, int nationalId);
}

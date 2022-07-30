package com.internship.test.person.service;


import com.internship.test.person.error.PersonNotFoundException;
import com.internship.test.person.node.Person;

import java.util.List;

public interface PersonService {
    Person savePerson(Person person);

    List<Person> fetchAllPerson();

    Person fetchPersonById(Long personId) throws PersonNotFoundException;

    void deletePerson(long personId);


    Person updatePerson(Long personId, Person person);
}

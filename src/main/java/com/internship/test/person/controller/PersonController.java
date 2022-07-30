package com.internship.test.person.controller;


import com.internship.test.person.error.PersonNotFoundException;
import com.internship.test.person.node.Person;
import com.internship.test.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/api/person" , consumes = {"application/json"})
    public Person savePerson(@Valid @RequestBody Person person){
        return personService.savePerson(person);
    }

    @PutMapping(value = "/api/person/{id}" , consumes = {"application/json"} )
    public Person updatePerson(@PathVariable("id") Long personId,
                              @Valid @RequestBody Person person
                               )

    {
        return personService.updatePerson(personId , person);
    }

   @GetMapping("/api/person")
    public List<Person> fetchAllPerson(){
        return personService.fetchAllPerson();
   }

    @GetMapping("/api/person/{id}")
    public Person fetchPersonById(@PathVariable("id") Long personId) throws PersonNotFoundException {
        return personService.fetchPersonById(personId);
    }

    @DeleteMapping("/api/person/{id}")
    public String deletePerson(@PathVariable("id") long personId){
        personService.deletePerson(personId);
        return "person deleted successfully!";
    }


}

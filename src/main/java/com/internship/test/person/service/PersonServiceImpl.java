package com.internship.test.person.service;

import com.internship.test.person.error.PersonNotFoundException;
import com.internship.test.person.node.Person;
import com.internship.test.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Override
    public Person savePerson(Person person) {
        Optional<Person> personByEmail = personRepository.findPersonByEmail(person.getEmail());
        if (personByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }

        Optional<Person> personByPhone = personRepository.findPersonByPhone(person.getPhone());
        if (personByPhone.isPresent()){
            throw new IllegalStateException("phone taken");
        }

        return personRepository.save(person) ;
    }



    @Override
    public List<Person> fetchAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public Person fetchPersonById(Long personId) throws PersonNotFoundException {
        Optional<Person> person = personRepository.findById(personId);

        if (!person.isPresent())
            throw new PersonNotFoundException("person not found !");

        return person.get();
    }

    @Override
    public void deletePerson(long personId) {
        boolean exists = personRepository.existsById(personId);

        if (!exists){
            throw new IllegalStateException("person with id  = " + personId + "does not exists");
        }

        personRepository.deleteById(personId);
    }

    @Override
    public Person updatePerson(Long personId, Person person) {
        Person personUpdate = personRepository.findById(personId).get();


        if (Objects.nonNull(person.getEmail()) && !"".equalsIgnoreCase(person.getEmail()) && !Objects.equals(person.getEmail() , personUpdate.getEmail())){

            Optional<Person> personByEmail = personRepository.findPersonByEmail(person.getEmail());
            if (personByEmail.isPresent()){
                throw new IllegalStateException("email taken");
            }

            personUpdate.setEmail(person.getEmail());
        }

        if (Objects.nonNull(person.getPhone()) && !"".equalsIgnoreCase(person.getPhone()) && !Objects.equals(person.getPhone() , personUpdate.getPhone())){

            Optional<Person> personByPhone = personRepository.findPersonByPhone(person.getPhone());
            if (personByPhone.isPresent()){
                throw new IllegalStateException("email taken");
            }

            personUpdate.setPhone(person.getPhone());
        }
        if (Objects.nonNull(person.getAddress()) && !"".equalsIgnoreCase(person.getAddress()) && !Objects.equals(person.getAddress() , personUpdate.getAddress())){
            personUpdate.setAddress(person.getAddress());
        }

        if (Objects.nonNull(person.getName()) && !"".equalsIgnoreCase(person.getName()) && !Objects.equals(person.getName() , personUpdate.getName())){
            personUpdate.setName(person.getName());
        }

        if (Objects.nonNull(person.getGender()) && !"".equalsIgnoreCase(person.getGender()) && !Objects.equals(person.getGender() , personUpdate.getGender())){

            personUpdate.setGender(person.getGender());
        }




        return personRepository.save(personUpdate);
    }


}

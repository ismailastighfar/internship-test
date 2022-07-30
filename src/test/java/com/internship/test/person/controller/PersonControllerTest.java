package com.internship.test.person.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.test.person.node.Person;
import com.internship.test.person.repository.PersonRepository;
import com.internship.test.person.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    ObjectMapper mapper;

    private Person person;
    Person person1 = new Person("ismail" , "male" , "ast@gmail.com" , "0601497640" , "tetouan");
    Person person2 = new Person("ouassima" , "female" , "ouassima@gmail.com" , "0601497645" , "mdiq");
    Person person3 = new Person("rida" , "male" , "rida@gmail.com" , "0601497660" , "paris");





    @Test
    void createPersonSuccess() throws Exception {
      Person inputperson = new Person("ismail" , "male" , "ast@gmail.com" , "0601497640" , "tetouan");

        Mockito.when(personService.savePerson(inputperson)).thenReturn(inputperson);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(inputperson)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePersonSuccess() throws Exception {
        Person updatePerson = new Person("ismail" , "male" , "ast@gmail.com" , "0601497640" , "tetouan");
        updatePerson.setId(1L);

        Mockito.when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));
        Mockito.when(personRepository.save(updatePerson)).thenReturn(updatePerson);

        mockMvc.perform(put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(updatePerson)))
                .andExpect(status().isOk());

    }

    /*@Test
    void updatePersonNotFound() throws Exception {
        Person updatePerson = new Person("test" , "male" , "test@gmail.com" , "0601497650" , "test");
        updatePerson.setId(5L);

        Mockito.when(personRepository.findById(updatePerson.getId())).thenReturn(null);
        Mockito.when(personRepository.save(updatePerson)).thenReturn(updatePerson);

        mockMvc.perform(put("/api/person/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(updatePerson)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ChangeSetPersister.NotFoundException))
                .andExpect(result -> assertEquals("person with ID 5 does not exist" , result.getResolvedException().getMessage()));


    }
*/


    @Test
    void fetchAllPersonSuccess() throws Exception {
        List<Person> persones = new ArrayList<>(Arrays.asList(person1,person2,person3));

        Mockito.when(personRepository.findAll()).thenReturn(persones);

        mockMvc.perform(get("/api/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void fetchPersonByIdSuccess() throws Exception {
        Mockito.when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        mockMvc.perform(get("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deletePersonSuccess() throws Exception {

        Mockito.when(personRepository.findById(person2.getId())).thenReturn(Optional.of(person2));

        mockMvc.perform(delete("/api/person/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /*@Test
    void deletePersonIdNotFound() throws Exception {

        Mockito.when(personRepository.findById(5L)).thenReturn(null);

        mockMvc.perform(delete("/api/person/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ChangeSetPersister.NotFoundException))
                .andExpect(result -> assertEquals("person with ID 5 does not exist" , result.getResolvedException().getMessage()));
    }*/
}
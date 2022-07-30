package com.internship.test.person.repository;

import com.internship.test.person.node.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person , Long> {


    Optional<Person> findPersonByEmail(String email);

    Optional<Person> findPersonByPhone(String phone);
}

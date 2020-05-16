package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PersonRepository extends JpaRepository<Person, Long> {



}

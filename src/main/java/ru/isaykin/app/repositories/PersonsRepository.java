package ru.isaykin.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaykin.app.entities.Person;

import java.util.Optional;

@Repository
public interface PersonsRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByFirstName(String firstName);


}

package ru.isaykin.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaykin.app.entities.Person;

@Repository
public interface PersonsRepository extends CrudRepository<Person, Long> {





}

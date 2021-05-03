package ru.isaykin.app.services;

import org.springframework.stereotype.Service;
import ru.isaykin.app.DTO.PersonDTO;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.exceptions.PersonNotFoundException;
import ru.isaykin.app.mappers.PersonMapper;
import ru.isaykin.app.repositories.PersonsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonsService {

    private final PersonsRepository personsRepository;

    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }


    public PersonDTO addPerson(PersonDTO personDTO) {
        Person person = PersonMapper.INSTANCE.fromPersonDTOToPerson(personDTO);
        person.setTelephoneBook(new TelephoneBook());
        person = personsRepository.save(person);
        personDTO = PersonMapper.INSTANCE.fromPersonToPersonDTO(person);
        return personDTO;
    }

    public PersonDTO getById(Long id) {
        Person person = personsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        PersonDTO result = PersonMapper.INSTANCE.fromPersonToPersonDTO(person);
        return result;
    }

    public List<PersonDTO> getList() {
        List<Person> personList = new ArrayList<>();
        personsRepository.findAll().iterator().forEachRemaining(personList::add);
        List<PersonDTO> personDTOList = personList
                .stream()
                .map(PersonMapper.INSTANCE::fromPersonToPersonDTO)
                .collect(Collectors.toList());
        return personDTOList;
    }

    public Long deleteById(Long id) {
        Person personToDelete = personsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        Long result = personToDelete.getPersonId();
        personsRepository.deleteById(id);
        if (personsRepository.findById(id).isPresent()) result = 0L;
        return result;
    }

    public PersonDTO updateById(Long id, String firstName, String lastName) {
        Person person = personsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        person.setFirstName(firstName);
        person.setLastName(lastName);
        PersonDTO result = PersonMapper.INSTANCE.fromPersonToPersonDTO(person);
        return result;


    }

}

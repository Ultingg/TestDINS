package ru.isaykin.app.services;

import org.springframework.stereotype.Service;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.exceptions.InvalidPersonException;
import ru.isaykin.app.exceptions.PersonNotFoundException;
import ru.isaykin.app.repositories.PersonsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.isaykin.app.mappers.PersonMapper.INSTANCE;

@Service
public class PersonsService {

    private final PersonsRepository personsRepository;

    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }


    public PersonDTO addPerson(PersonDTO personDTO) {
        Person person = INSTANCE.fromPersonDTOToPerson(personDTO);
        person.setTelephoneBook(new TelephoneBook());
        person = personsRepository.save(person);
        PersonDTO result = INSTANCE.fromPersonToPersonDTO(person);
        return result;
    }

    public PersonDTO getPersonById(Long id) {
        Person person = personsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        PersonDTO result = INSTANCE.fromPersonToPersonDTO(person);
        return result;
    }

    public List<PersonDTO> getList() {
        List<Person> personList = new ArrayList<>();
        personsRepository.findAll().iterator().forEachRemaining(personList::add);
        List<PersonDTO> personDTOListResult = personList
                .stream()
                .map(INSTANCE::fromPersonToPersonDTO)
                .collect(Collectors.toList());
        return personDTOListResult;
    }

    public Map<String, Object> deletePersonById(Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        Person personToDelete = personsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        personsRepository.deleteById(id);
        PersonDTO deletedPersonDTO = INSTANCE.fromPersonToPersonDTO(personToDelete);
        resultMap.put("Message:", "Person was deleted.");
        resultMap.put("Person:", deletedPersonDTO);
        return resultMap;
    }

    public PersonDTO updatePersonById(Long id, PersonDTO personDTO) {
        Person person = personsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        if (personDTO == null) throw new InvalidPersonException("You send invalid person for update.");
        if (personDTO.getFirstName() != null) person.setFirstName(personDTO.getFirstName());
        if (personDTO.getLastName() != null) person.setLastName(personDTO.getLastName());
        person = personsRepository.save(person);
        PersonDTO result = INSTANCE.fromPersonToPersonDTO(person);
        return result;
    }

    public PersonDTO getPersonByName(String firstName) {
        Person person = personsRepository.findByFirstName(firstName)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong first name."));
        PersonDTO personDTOResult = INSTANCE.fromPersonToPersonDTO(person);
        return personDTOResult;
    }
}

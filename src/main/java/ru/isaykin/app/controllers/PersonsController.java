package ru.isaykin.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.services.PersonsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("persons")
public class PersonsController {
    private final PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @PostMapping
    public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO personResult = personsService.addPerson(personDTO);
        return new ResponseEntity<>(personResult, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getListOfPersons() {
        List<PersonDTO> personDTOList = personsService.getList();
        return new ResponseEntity<>(personDTOList, OK);
    }

    @GetMapping("person")
    public ResponseEntity<PersonDTO> getPersonByName(@RequestParam String firstName) {
        PersonDTO personResult = personsService.getPersonByName(firstName);
        return new ResponseEntity<>(personResult, OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO personResult = personsService.getPersonById(id);
        return new ResponseEntity<>(personResult, OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deletePersonById(@PathVariable Long id) {
        Map<String, Object> resultMap = personsService.deletePersonById(id);
        return new ResponseEntity<>(resultMap, OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id,
                                                  @Valid @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPersonDTO = personsService.updatePersonById(id, personDTO);
        return new ResponseEntity<>(updatedPersonDTO, OK);
    }


}

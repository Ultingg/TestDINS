package ru.isaykin.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.services.PersonsService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
        PersonDTO personResult = personsService.getByName(firstName);
        return new ResponseEntity<>(personResult, OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO personResult = personsService.getById(id);
        return new ResponseEntity<>(personResult, OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<StringBuilder> deleteById(@PathVariable Long id) {
        ResponseEntity<StringBuilder> result;
        StringBuilder response = new StringBuilder();
        Long resultLong = personsService.deleteById(id);
        if (resultLong == 0L) {
            response.append("Person wasn't deleted. Repeat request, please.");
            result = new ResponseEntity<>(response, BAD_REQUEST);
        } else {
            response.append("Person id = ").append(resultLong).append(" was deleted.");
            result = new ResponseEntity<>(response, OK);
        }
        return result;
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id,
                                                  @Valid @RequestBody PersonDTO personDTO) {
        PersonDTO UpdatedPersonDTO = personsService.updateById(id, personDTO);
        return new ResponseEntity<>(UpdatedPersonDTO, OK);
    }


}

package contorllersTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.isaykin.app.controllers.PersonsController;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.services.PersonsService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class PersonControllerTests {


    private PersonsController personsController;
    private PersonsService personsService;

    @BeforeEach
    public void setUp() {
        personsService = mock(PersonsService.class);
        personsController = new PersonsController(personsService);
    }


    @Test
    public void addPerson_valid_Person() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        PersonDTO expectedPersonDTO = new PersonDTO();
        expectedPersonDTO.setId(1L);
        expectedPersonDTO.setFirstName("Adam");
        expectedPersonDTO.setLastName("Man");
        ResponseEntity<PersonDTO> expectedResponse = new ResponseEntity<>(expectedPersonDTO, CREATED);
        when(personsService.addPerson(personDTO)).thenReturn(expectedPersonDTO);

        ResponseEntity<PersonDTO> actual = personsController.addPerson(personDTO);

        assertEquals(expectedResponse, actual);
        verify(personsService, times(1)).addPerson(personDTO);
        verify(personsService, times(1)).addPerson(any(PersonDTO.class));
    }

    @Test
    public void getListOfPersons_valid_ListOfPersons() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        PersonDTO personDTO2 = new PersonDTO();
        personDTO2.setId(2L);
        personDTO2.setFirstName("Eve");
        personDTO2.setLastName("Woman");
        List<PersonDTO> dtoList = Arrays.asList(personDTO, personDTO2);
        ResponseEntity<List<PersonDTO>> expectedResponse = new ResponseEntity<>(dtoList, OK);
        when(personsService.getList()).thenReturn(dtoList);

        ResponseEntity<List<PersonDTO>> actual = personsController.getListOfPersons();

        assertEquals(expectedResponse, actual);
        verify(personsService, times(1)).getList();
    }

    @Test
    public void getPersonByName_valid_PersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        when(personsService.getPersonByName("Adam")).thenReturn(personDTO);
        ResponseEntity<PersonDTO> expectedResponse = new ResponseEntity<>(personDTO, OK);

        ResponseEntity<PersonDTO> actual = personsController.getPersonByName("Adam");

        assertEquals(expectedResponse, actual);
        verify(personsService, times(1)).getPersonByName("Adam");
        verify(personsService, times(1)).getPersonByName(anyString());
    }

    @Test
    public void getPersonById_valid_PersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        when(personsService.getPersonById(1L)).thenReturn(personDTO);
        ResponseEntity<PersonDTO> expectedResponse = new ResponseEntity<>(personDTO, OK);

        ResponseEntity<PersonDTO> actual = personsController.getPersonById(1L);

        assertEquals(expectedResponse, actual);
        verify(personsService, times(1)).getPersonById(1L);
        verify(personsService, times(1)).getPersonById(anyLong());
    }

    @Test
    public void deletePersonById_valid_PersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("Message:", "Person was deleted.");
        resultMap.put("Person:", personDTO);
        ResponseEntity<Map<String, Object>> expectedResponse = new ResponseEntity<>(resultMap, OK);
        when(personsService.deletePersonById(1L)).thenReturn(resultMap);

        ResponseEntity<Map<String, Object>> actual = personsController.deletePersonById(1L);

        assertEquals(expectedResponse, actual);
        verify(personsService, times(1)).deletePersonById(1L);
        verify(personsService, times(1)).deletePersonById(anyLong());
    }

    @Test
    public void updatePerson_valid_PersonDTO() {
        PersonDTO expectedPersonDTO = new PersonDTO();
        expectedPersonDTO.setId(1L);
        expectedPersonDTO.setFirstName("Adam");
        expectedPersonDTO.setLastName("Man");
        ResponseEntity<PersonDTO> expectedResponse = new ResponseEntity<>(expectedPersonDTO, OK);
        when(personsService.updatePersonById(1L, expectedPersonDTO)).thenReturn(expectedPersonDTO);

        ResponseEntity<PersonDTO> actual = personsController.updatePerson(1L, expectedPersonDTO);

        assertEquals(expectedResponse, actual);
        verify(personsService, times(1)).updatePersonById(1L, expectedPersonDTO);
        verify(personsService, times(1)).updatePersonById(anyLong(), any(PersonDTO.class));
    }

}

package servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.exceptions.InvalidNoteException;
import ru.isaykin.app.exceptions.InvalidPersonException;
import ru.isaykin.app.exceptions.PersonNotFoundException;
import ru.isaykin.app.repositories.PersonsRepository;
import ru.isaykin.app.services.PersonsService;

import java.util.*;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PersonsServiceTests {

    private PersonsService personsService;
    private PersonsRepository personsRepository;

    @BeforeEach
    void setUp() {
        personsRepository = mock(PersonsRepository.class);
        personsService = new PersonsService(personsRepository);
    }


    @Test
    @DisplayName("addPerson method test.")
    public void addPerson_valid_PersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook());
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.save(any(Person.class))).thenReturn(personToReturnFromDB);

        PersonDTO actual = personsService.addPerson(personDTO);

        assertEquals("Adam", actual.getFirstName(),"Actual firstName unequals to expected firstName.");
        assertEquals("Man", actual.getLastName(), "Actual lastName unequals to expected lastName");
        assertEquals(1L, actual.getId(), "Actual id unequals to expected id");
        verify(personsRepository, times(1)).save(any(Person.class));
    }



    @Test
    public void getPersonById_valid_PersonDTO() {
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook());
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        PersonDTO actual = personsService.getPersonById(1L);

        assertEquals("Adam", actual.getFirstName());
        assertEquals("Man", actual.getLastName());
        assertEquals(1L, actual.getId());
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }
    @Test

    public void getPersonById_invalidId_PersonNotFoundException(){
        assertThrows(PersonNotFoundException.class, () ->personsService.getPersonById(1L));
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }
    @Test
    public void getList_valid_ListOfPersons() {
        Person person1 = new Person("Adam", "Man", new TelephoneBook());
        person1.setPersonId(1L);
        Person person2 = new Person("Eve", "Woman", new TelephoneBook());
        person2.setPersonId(2L);
        List<Person> listToReturnFromDB = Arrays.asList(person1, person2);
        when(personsRepository.findAll()).thenReturn(listToReturnFromDB);

        List<PersonDTO> personDTOList = personsService.getList();

        assertEquals("Adam", personDTOList.get(0).getFirstName());
        assertEquals("Man", personDTOList.get(0).getLastName());
        assertEquals(1L, personDTOList.get(0).getId());
        assertEquals("Eve", personDTOList.get(1).getFirstName());
        assertEquals("Woman", personDTOList.get(1).getLastName());
        assertEquals(2L, personDTOList.get(1).getId());
        verify(personsRepository, times(1)).findAll();
    }

    @Test
    public void deletePersonById_valid_ValidMap() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        personDTO.setId(1L);
        Map<String, Object> mapExpected = new HashMap<>();
        mapExpected.put("Message:", "Person was deleted.");
        mapExpected.put("Person:", personDTO);
        Person personFromDB = new Person("Adam", "Man", new TelephoneBook());
        personFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));

        Map<String, Object> actual = personsService.deletePersonById(1L);

        assertEquals(mapExpected, actual);
        verify(personsRepository, times(1)).deleteById(1L);
        verify(personsRepository, times(1)).deleteById(anyLong());
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }
    @Test
    public void deletePersonByID_invalidId_PersonNotFoundException(){
        assertThrows(PersonNotFoundException.class, () -> personsService.deletePersonById(1L));
        verify(personsRepository, times(0)).deleteById(1L);
        verify(personsRepository, times(0)).deleteById(anyLong());
    }

    @Test
    public void updatePersonById_valid_validPerson() {
        PersonDTO personDTOExpected = new PersonDTO();
        personDTOExpected.setFirstName("Adam");
        personDTOExpected.setLastName("First Man");
        personDTOExpected.setId(1L);
        Person personFromDB = new Person("Adam", "Man", new TelephoneBook());
        personFromDB.setPersonId(1L);
        Person savedPersonFromDB = new Person("Adam", "First Man", new TelephoneBook());
        savedPersonFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));
        when(personsRepository.save(any(Person.class))).thenReturn(savedPersonFromDB);

        PersonDTO actual = personsService.updatePersonById(1L, personDTOExpected);

        assertEquals(personDTOExpected, actual);
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
        verify(personsRepository, times(1)).save(personFromDB);
        verify(personsRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void updatePersonById_invalidId_PersonNotFoundException(){
        PersonDTO personDTOExpected = new PersonDTO();
        personDTOExpected.setFirstName("Adam");
        personDTOExpected.setLastName("First Man");
        personDTOExpected.setId(1L);

        assertThrows(PersonNotFoundException.class, () -> personsService.updatePersonById(1L, personDTOExpected));
        assertThrows(PersonNotFoundException.class, () -> personsService.updatePersonById(null, personDTOExpected));

        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
        verify(personsRepository, times(0)).save(any(Person.class));
    }
    @Test
    public void updatePersonById_invalidId_InvalidPersonException(){
        Person personFromDB = new Person("Adam", "Man", new TelephoneBook());
        personFromDB.setPersonId(1L);
        Person savedPersonFromDB = new Person("Adam", "First Man", new TelephoneBook());
        savedPersonFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));

        assertThrows(InvalidPersonException.class, () -> personsService.updatePersonById(1L, null));
    }


    @Test
    public void getPersonByName_valid_personDTO() {
        Person personFromDB = new Person("Eve", "Woman", new TelephoneBook());
        personFromDB.setPersonId(1L);
        PersonDTO personDTOExpected = new PersonDTO();
        personDTOExpected.setFirstName("Eve");
        personDTOExpected.setLastName("Woman");
        personDTOExpected.setId(1L);
        when(personsRepository.findByFirstName("Eve")).thenReturn(of(personFromDB));

        PersonDTO actual = personsService.getPersonByName("Eve");

        assertEquals(personDTOExpected, actual);
        verify(personsRepository, times(1)).findByFirstName("Eve");
        verify(personsRepository, times(1)).findByFirstName(anyString());
    }
    @Test
    public void getPersonByName_invalidId_PersonNotFoundException(){
        assertThrows(PersonNotFoundException.class, () -> personsService.getPersonByName("Some name"));
        assertThrows(PersonNotFoundException.class, () -> personsService.getPersonByName(null));
        verify(personsRepository, times(1)).findByFirstName("Some name");
        verify(personsRepository, times(1)).findByFirstName(null);
        verify(personsRepository, times(1)).findByFirstName(anyString());
    }

}

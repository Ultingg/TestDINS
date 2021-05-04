package servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.repositories.PersonsRepository;
import ru.isaykin.app.services.PersonsService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersonsServiceTest {

    private PersonsService personsService;
    private PersonsRepository personsRepository;

    @BeforeEach
    void setUp() {
        personsRepository = mock(PersonsRepository.class);
        personsService = new PersonsService(personsRepository);
    }


    @Test
    public void addPerson_valid_CREATED() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("Adam");
        personDTO.setLastName("Man");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook());
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.save(any(Person.class))).thenReturn(personToReturnFromDB);

        PersonDTO personDTOReturned = personsService.addPerson(personDTO);

        assertEquals("Adam", personDTOReturned.getFirstName());
        assertEquals("Man", personDTOReturned.getLastName());
        assertEquals(1L, personDTOReturned.getId());
        verify(personsRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void getPersonById_valid_OK() {
        Long personDTOId = 1L;
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook());
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(java.util.Optional.of(personToReturnFromDB));

        PersonDTO personDTOReturned = personsService.getPersonById(1L);

        assertEquals("Adam", personDTOReturned.getFirstName());
        assertEquals("Man", personDTOReturned.getLastName());
        assertEquals(1L, personDTOReturned.getId());
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getList_valid_OK() {
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
}

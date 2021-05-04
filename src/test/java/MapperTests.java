import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.mappers.NoteMapper;
import ru.isaykin.app.mappers.PersonMapper;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapperTests {

    @Test
    void fromPersonToPersonDTO() {
        Person person = new Person("Adam", "Man", new TelephoneBook(Set.of()));

        PersonDTO personDTO = PersonMapper.INSTANCE.fromPersonToPersonDTO(person);

        assertEquals("Adam", personDTO.getFirstName());
        assertEquals("Man", personDTO.getLastName());
    }

    @Test
    void fromPersonDTOToPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("Eve");
        personDTO.setLastName("Woman");

        Person person = PersonMapper.INSTANCE.fromPersonDTOToPerson(personDTO);

        assertEquals("Eve", person.getFirstName());
        assertEquals("Woman", person.getLastName());
    }

    @Test
    void fromNoteToNoteDTO() {
        Note note = new Note("Iosif", "+74951234567");

        NoteDTO noteDTO = NoteMapper.INSTANCE.fromNoteToNoteDTO(note);

        assertEquals("Iosif", noteDTO.getContactName());
        assertEquals("+74951234567", noteDTO.getTelephoneNumber());
    }

    @Test
    void fromNoteDTOToNote() {
        NoteDTO noteDTO = NoteDTO.builder()
                .contactName("Nikita")
                .telephoneNumber("+74959874561")
                .build();


        Note note = NoteMapper.INSTANCE.fromNoteDTOToNote(noteDTO);

        assertEquals("Nikita", note.getContactName());
        assertEquals("+74959874561", note.getTelephoneNumber());
    }

}

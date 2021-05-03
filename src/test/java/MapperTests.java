import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.isaykin.app.DTO.NoteDTO;
import ru.isaykin.app.DTO.PersonDTO;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.mappers.NoteMapper;
import ru.isaykin.app.mappers.PersonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapperTests {

    @Test
    void fromPersonToPersonDTO() {
        Person person = new Person("Adam","Man", new TelephoneBook());

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
        NoteDTO noteDTO = new NoteDTO("Nikita", "+74959874561");

        Note note = NoteMapper.INSTANCE.fromNoteDTOToNote(noteDTO);

        assertEquals("Nikita", note.getContactName());
        assertEquals("+74959874561",note.getTelephoneNumber());
    }

}

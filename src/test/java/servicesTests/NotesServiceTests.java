package servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.exceptions.InvalidNoteException;
import ru.isaykin.app.exceptions.NoteNotFoundException;
import ru.isaykin.app.exceptions.PersonNotFoundException;
import ru.isaykin.app.repositories.NotesRepository;
import ru.isaykin.app.repositories.PersonsRepository;
import ru.isaykin.app.services.NotesService;

import java.util.Set;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class NotesServiceTests {

    private NotesService notesService;
    private NotesRepository notesRepository;
    private PersonsRepository personsRepository;

    @BeforeEach
    public void setUp() {
        notesRepository = mock(NotesRepository.class);
        personsRepository = mock(PersonsRepository.class);
        notesService = new NotesService(notesRepository, personsRepository);
    }

    @Test
    public void getNoteById_validID_NoteDTO() {
        NoteDTO expectedNoteDTO = new NoteDTO();
        expectedNoteDTO.setId(1L);
        expectedNoteDTO.setContactName("Eve");
        expectedNoteDTO.setTelephoneNumber("+00000000002");
        Note note = new Note("Eve","+00000000002");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook(Set.of(note)));
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        NoteDTO actual = notesService.getNoteById(1L,1L);

        assertEquals(expectedNoteDTO, actual);
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }
    @Test
    public void getNoteById_invalidPersonId_PersonNotFoundException() {

        assertThrows(PersonNotFoundException.class, () -> notesService.getNoteById(1L, 1L));
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void  getNoteById_invalidNoteId_NoteNotFoundException() {
        NoteDTO expectedNoteDTO = new NoteDTO();
        expectedNoteDTO.setId(1L);
        expectedNoteDTO.setContactName("Eve");
        expectedNoteDTO.setTelephoneNumber("+00000000002");
        Note note = new Note("Eve","+00000000002");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook(Set.of(note)));
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        assertThrows(NoteNotFoundException.class,() -> notesService.getNoteById(1L,10L));
        assertThrows(NoteNotFoundException.class,() -> notesService.getNoteById(1L,-10L));
        assertThrows(InvalidNoteException.class,() -> notesService.getNoteById(1L,null));
    }
    @Test
    public void getNoteById_invalidNoteId_InvalidNoteException() {
        NoteDTO expectedNoteDTO = new NoteDTO();
        expectedNoteDTO.setId(1L);
        expectedNoteDTO.setContactName("Eve");
        expectedNoteDTO.setTelephoneNumber("+00000000002");
        Note note = new Note("Eve","+00000000002");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook(Set.of(note)));
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        assertThrows(InvalidNoteException.class,() -> notesService.getNoteById(1L,null));
    }





}

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

import java.util.*;

import static java.util.Optional.*;
import static java.util.Optional.of;
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
        NoteDTO expectedNoteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        Note note = new Note("Eve", "+00000000002");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook(Set.of(note)));
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        NoteDTO actual = notesService.getNoteById(1L, 1L);

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
    public void getNoteById_invalidNoteId_NoteNotFoundException() {
        Note note = new Note("Eve", "+00000000002");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook(Set.of(note)));
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        assertThrows(NoteNotFoundException.class, () -> notesService.getNoteById(1L, 10L));
        assertThrows(NoteNotFoundException.class, () -> notesService.getNoteById(1L, -10L));
        assertThrows(InvalidNoteException.class, () -> notesService.getNoteById(1L, null));

        verify(personsRepository, times(3)).findById(1L);
        verify(personsRepository, times(3)).findById(anyLong());
    }

    @Test
    public void getNoteById_invalidNoteId_InvalidNoteException() {
        Note note = new Note("Eve", "+00000000002");
        Person personToReturnFromDB = new Person("Adam", "Man", new TelephoneBook(Set.of(note)));
        personToReturnFromDB.setPersonId(1L);
        when(personsRepository.findById(1L)).thenReturn(of(personToReturnFromDB));

        assertThrows(InvalidNoteException.class, () -> notesService.getNoteById(1L, null));

        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getNoteFromPersonBookByTelephoneNumber_valid_NoteDTO() {
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));
        NoteDTO expectedNoteDTO = new NoteDTO(1L, "Eve", "+00000000002");

        NoteDTO actual = notesService.getNoteFromPersonBookByTelephoneNumber(1L, "+00000000002");

        assertEquals(expectedNoteDTO, actual);
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getNoteFromPersonBookByTelephoneNumber_invalidPersonId_PersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> notesService.getNoteFromPersonBookByTelephoneNumber(1L, "+00000000001"));

        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void addNoteToPersonById_valid_NoteDTO() {
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        Note note = new Note("Father", "+00000000000");
        personFromDB.addNote(note);
        when(personsRepository.existsById(1L)).thenReturn(true);
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));
        NoteDTO expectedNoteDTO = new NoteDTO("Father", "+00000000000");

        NoteDTO actual = notesService.addNoteToPersonById(1L, expectedNoteDTO);
        expectedNoteDTO.setId(3L);

        assertEquals(expectedNoteDTO, actual);

        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
        verify(personsRepository, times(1)).existsById(1L);
        verify(personsRepository, times(1)).existsById(anyLong());
    }

    @Test
    public void addNoteToPersonById_invalidPersonId_PersonNOtFoundException() {
        assertThrows(PersonNotFoundException.class, () -> notesService.addNoteToPersonById(1L, new NoteDTO()));

        verify(personsRepository, times(1)).existsById(1L);
        verify(personsRepository, times(1)).existsById(anyLong());
    }

    @Test
    public void addNoteToPersonById_nullPersonId_PersonNOtFoundException() {
        assertThrows(PersonNotFoundException.class, () -> notesService.addNoteToPersonById(null, new NoteDTO()));

        verify(personsRepository, times(1)).existsById(null);
        verify(personsRepository, times(1)).existsById(any());
    }


    @Test
    public void getListOfNoteDTOById_valid_ListOfNotes() {
        List<NoteDTO> expectedList = getListOfNotesDTO();
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));

        List<NoteDTO> actual = notesService.getListOfNoteDTOById(1L);

        assertEquals(expectedList, actual);
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    private Set<Note> getSetOfNotes() {
        Note note = new Note("Eve", "+00000000002");
        Note note1 = new Note("Sanke", "+00000000009");
        note.setNoteId(1L);
        note1.setNoteId(2L);
        Set<Note> noteSet = new HashSet();
        noteSet.add(note);
        noteSet.add(note1);

        return noteSet;
    }

    private List<NoteDTO> getListOfNotesDTO() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        NoteDTO noteDTO2 = new NoteDTO(2L, "Sanke", "+00000000009");
        return Arrays.asList(noteDTO, noteDTO2);
    }

    @Test
    public void getListOfNoteDTOById_invalidPersonId_PersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> notesService.getListOfNoteDTOById(1L));

        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getListOfNoteDTOById_nullPersonId_PersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> notesService.getListOfNoteDTOById(null));

        verify(personsRepository, times(1)).findById(null);
        verify(personsRepository, times(1)).findById(any());
    }

    @Test
    public void deleteNoteByIdFromPersonBook_validPersonId_MapPersonDTO() {
        Map<String, Object> expectedMap = new HashMap<>();
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        Note note = new Note("Eve", "+00000000002");
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        expectedMap.put("Message: ", "Note was deleted");
        expectedMap.put("Note: ", noteDTO);
        when(notesRepository.findByTelephoneNumber("+00000000002")).thenReturn(of(note));
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));

        Map<String, Object> actual = notesService.deleteNoteByIdFromPersonBook(1L, 1L);

        assertEquals(expectedMap, actual);
        verify(notesRepository, times(1)).findByTelephoneNumber(anyString());
        verify(notesRepository, times(1)).findByTelephoneNumber("+00000000002");
        verify(notesRepository, times(1)).delete(note);
        verify(notesRepository, times(1)).delete(any(Note.class));
        verify(personsRepository, times(1)).findById(1L);
        verify(personsRepository, times(1)).findById(any());
    }

    @Test
    public void deleteNoteByIdFromPersonBook_invalidNoteId_NoteNotFoundException() {
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));

        assertThrows(NoteNotFoundException.class, ()-> notesService.deleteNoteByIdFromPersonBook(1L,4L));

        verify(personsRepository,times(1)).findById(1L);
        verify(personsRepository,times(1)).findById(anyLong());
        verify(notesRepository,times(0)).delete(any(Note.class));
    }


    @Test
    public void updateNoteInPersonBook_valid_NoteDTO() {
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        Note oldNote = new Note( "Snake", "+00000000009");
        Note newNote = new Note( "Snake with apple", "+00000000099");
        NoteDTO expectedNoteDTO = new NoteDTO(2L, "Snake with apple", "+00000000099");
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));
        when(notesRepository.findByTelephoneNumber("+00000000009")).thenReturn(of(oldNote));
        when(notesRepository.save(any(Note.class))).thenReturn(newNote);

        NoteDTO actual = notesService.updateNoteInPersonBook(1L,2L,"Snake with apple","+00000000099");

        assertEquals(expectedNoteDTO,actual);
        verify(personsRepository,times(1)).findById(1L);
        verify(personsRepository,times(1)).findById(anyLong());
        verify(notesRepository, times(1)).findByTelephoneNumber(anyString());
        verify(notesRepository, times(1)).findByTelephoneNumber("+00000000009");
        verify(notesRepository, times(1)).save(any(Note.class));
    }

    @Test
    public void updateNoteInPersonBook_nullPersonId_PersonNotFoundException() {

        assertThrows(PersonNotFoundException.class, ()->notesService.updateNoteInPersonBook(null,1L,"name", "+00000000009"));

        verify(personsRepository,times(1)).findById(null);
        verify(personsRepository,times(1)).findById(any());
    }
    @Test
    public void updateNoteInPersonBook_nullNoteId_NoteNotFoundException() {
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));

        assertThrows(NoteNotFoundException.class, ()->notesService.updateNoteInPersonBook(1L,null,"name", "+00000000009"));

        verify(personsRepository,times(1)).findById(1L);
        verify(personsRepository,times(1)).findById(anyLong());
    }
    @Test
    public void updateNoteInPersonBook_nullNameAndTelephone_NoteDTO() {
        Person personFromDB = new Person("Adam", "+00000000001", new TelephoneBook(getSetOfNotes()));
        Note oldNote = new Note( "Snake", "+00000000009");
        NoteDTO expectedNoteDTO = new NoteDTO(2L, "Snake", "+00000000009");
        when(personsRepository.findById(1L)).thenReturn(of(personFromDB));
        when(notesRepository.findByTelephoneNumber("+00000000009")).thenReturn(of(oldNote));
        when(notesRepository.save(any(Note.class))).thenReturn(oldNote);

        NoteDTO actual = notesService.updateNoteInPersonBook(1L,2L,null,null);

        assertEquals(expectedNoteDTO,actual);
        verify(personsRepository,times(1)).findById(1L);
        verify(personsRepository,times(1)).findById(anyLong());
        verify(notesRepository, times(1)).findByTelephoneNumber(anyString());
        verify(notesRepository, times(1)).findByTelephoneNumber("+00000000009");
        verify(notesRepository, times(1)).save(any(Note.class));
    }


}

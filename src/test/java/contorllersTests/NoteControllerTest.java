package contorllersTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.isaykin.app.controllers.NotesController;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.services.NotesService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class NoteControllerTest {


    private NotesService notesService;
    private NotesController notesController;

    @BeforeEach
    public void setUp() {
        notesService = mock(NotesService.class);
        notesController = new NotesController(notesService);
    }


    @Test
    public void getByIdInPersonBook_valid_NotDTO_OK() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        when(notesService.getNoteById(1L, 1L)).thenReturn(noteDTO);
        ResponseEntity<NoteDTO> expectedResponse = new ResponseEntity<>(noteDTO, OK);

        ResponseEntity<NoteDTO> actual = notesController.getByIdInPersonBook(1L, 1L);

        assertEquals(expectedResponse, actual);
        verify(notesService, times(1)).getNoteById(1L, 1L);
        verify(notesService, times(1)).getNoteById(anyLong(), anyLong());
    }

    @Test
    public void getListOfNotesByIdOfPerson_valid_ListOfNotDTO_OK() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        NoteDTO noteDTO2 = new NoteDTO(2L, "Snake", "+00000000009");
        List<NoteDTO> noteDTOList = Arrays.asList(noteDTO, noteDTO2);
        when(notesService.getListOfNoteDTOById(1L)).thenReturn(noteDTOList);
        ResponseEntity<List<NoteDTO>> expectedResponse = new ResponseEntity<>(noteDTOList, OK);

        ResponseEntity<List<NoteDTO>> actual = notesController.getListOfNotesByIdOfPerson(1L);

        assertEquals(expectedResponse, actual);
        verify(notesService, times(1)).getListOfNoteDTOById(1L);
        verify(notesService, times(1)).getListOfNoteDTOById(anyLong());
    }

    @Test
    public void getNoteByTelephoneNumberInPersonBook_valid_NotDTO_OK() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        when(notesService.getNoteFromPersonBookByTelephoneNumber(1L, "+00000000002")).thenReturn(noteDTO);
        ResponseEntity<NoteDTO> expectedResponse = new ResponseEntity<>(noteDTO, OK);

        ResponseEntity<NoteDTO> actual = notesController.getNoteByTelephoneNumberInPersonBook(1L, "+00000000002");

        assertEquals(expectedResponse, actual);
        verify(notesService, times(1)).getNoteFromPersonBookByTelephoneNumber(1L, "+00000000002");
        verify(notesService, times(1)).getNoteFromPersonBookByTelephoneNumber(anyLong(), anyString());
    }

    @Test
    public void addNoteToPersonBook_valid_NotDTO_OK() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        when(notesService.addNoteToPersonById(1L, noteDTO)).thenReturn(noteDTO);
        ResponseEntity<NoteDTO> expectedResponse = new ResponseEntity<>(noteDTO, CREATED);

        ResponseEntity<NoteDTO> actual = notesController.addNoteToPersonBook(1L, noteDTO);

        assertEquals(expectedResponse, actual);
        verify(notesService, times(1)).addNoteToPersonById(1L, noteDTO);
        verify(notesService, times(1)).addNoteToPersonById(anyLong(), any(NoteDTO.class));
    }

    @Test
    public void updateNoteInPersonBook_valid_NotDTO_OK() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        when(notesService.updateNoteInPersonBook(1L, 1L, "Eve", "+00000000002")).thenReturn(noteDTO);
        ResponseEntity<NoteDTO> expectedResponse = new ResponseEntity<>(noteDTO, OK);

        ResponseEntity<NoteDTO> actual = notesController.updateNoteInPersonBook(1L, 1L, noteDTO);

        assertEquals(expectedResponse, actual);
        verify(notesService, times(1)).updateNoteInPersonBook(1L, 1L, "Eve", "+00000000002");
        verify(notesService, times(1)).updateNoteInPersonBook(anyLong(), anyLong(), anyString(), anyString());
    }

    @Test
    public void deleteNoteFromPersonBook_valid_NotDTO_OK() {
        NoteDTO noteDTO = new NoteDTO(1L, "Eve", "+00000000002");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("Message:", "Note was deleted.");
        resultMap.put("Person:", noteDTO);

        when(notesService.deleteNoteByIdFromPersonBook(1L, 1L)).thenReturn(resultMap);
        ResponseEntity<Map<String, Object>> expectedResponse = new ResponseEntity<>(resultMap, OK);

        ResponseEntity<Map<String, Object>> actual = notesController.deleteNoteFromPersonBook(1L, 1L);

        assertEquals(expectedResponse, actual);
        verify(notesService, times(1)).deleteNoteByIdFromPersonBook(1L, 1L);
        verify(notesService, times(1)).deleteNoteByIdFromPersonBook(anyLong(), anyLong());
    }


}

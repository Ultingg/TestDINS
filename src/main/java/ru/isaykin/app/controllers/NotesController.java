package ru.isaykin.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.services.NotesService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("persons")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("{personId}/notes/{noteId}")
    public ResponseEntity<NoteDTO> getByIdInPersonBook(@PathVariable Long personId,
                                                       @PathVariable Long noteId) {
        NoteDTO result = notesService.getNoteById(personId, noteId);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("{personId}/notes")
    public ResponseEntity<List<NoteDTO>> getListOfNotesByIdOfPerson(@PathVariable Long personId) {
        List<NoteDTO> resultList = notesService.getListOfNoteDTOById(personId);
        return new ResponseEntity<>(resultList, OK);
    }

    @GetMapping("{personId}/notes/note")
    public ResponseEntity<NoteDTO> getNoteByTelephoneNumberInPersonBook(@PathVariable Long personId,
                                                                        @RequestParam String telephoneNumber) {
        NoteDTO noteDTO = notesService.getNoteFromPersonBookByTelephoneNumber(personId, telephoneNumber);
        return new ResponseEntity<>(noteDTO, OK);
    }

    @PostMapping("{personId}/notes")
    public ResponseEntity<NoteDTO> addNoteToPersonBook(@PathVariable Long personId,
                                                       @Valid @RequestBody NoteDTO noteDTO) {
        NoteDTO resultNoteDTO = notesService.addNoteToPersonById(personId, noteDTO);
        return new ResponseEntity<>(resultNoteDTO, CREATED);
    }

    @PutMapping("{personId}/notes/{noteId}")
    public ResponseEntity<NoteDTO> updateNoteInPersonBook(@PathVariable Long personId,
                                                          @PathVariable Long noteId,
                                                          @Valid @RequestBody NoteDTO noteDTO) {
        NoteDTO updatedNoteDTO = notesService.updateNoteInPersonBook(
                personId, noteId, noteDTO.getContactName(), noteDTO.getTelephoneNumber());

        return new ResponseEntity<>(updatedNoteDTO, OK);
    }

    @DeleteMapping("{personId}/notes/{noteId}")
    public ResponseEntity<Map<String, Object>> deleteNoteFromPersonBook(@PathVariable Long personId,
                                                                        @PathVariable Long noteId) {
        Map<String, Object> resultMap = notesService.deleteNoteByIdFromPersonBook(personId, noteId);
        return new ResponseEntity<>(resultMap, OK);
    }

}

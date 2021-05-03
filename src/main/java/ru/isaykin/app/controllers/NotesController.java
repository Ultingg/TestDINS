package ru.isaykin.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaykin.app.DTO.NoteDTO;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.repositories.NotesRepository;
import ru.isaykin.app.services.NotesService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("persons")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("{id}/notes/{note_id}")
    public ResponseEntity<NoteDTO> getById(@PathVariable Long id,
                                           @PathVariable Long note_id) {

        NoteDTO result = notesService.getNoteById(id, note_id);
        return new ResponseEntity<>(result,OK);
    }
    @GetMapping("{id}/notes")
    public ResponseEntity<List<NoteDTO>> getListOfNotesById(@PathVariable Long id) {
        List<NoteDTO> resultList = notesService.getListOfNoteDTOById(id);
        return new ResponseEntity<>(resultList, OK);
    }

    @PostMapping("{id}/notes/")
    public ResponseEntity<NoteDTO> addNoteToPerson(@PathVariable Long id,
                                                   @RequestBody NoteDTO noteDTO) {
    NoteDTO resultNoteDTO = notesService.addNoteToPersonById(id,noteDTO);
    return new ResponseEntity<>(resultNoteDTO, CREATED);
    }
}

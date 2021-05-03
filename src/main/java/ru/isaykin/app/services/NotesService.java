package ru.isaykin.app.services;

import org.springframework.stereotype.Service;
import ru.isaykin.app.DTO.NoteDTO;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.exceptions.NoteNotFoundException;
import ru.isaykin.app.exceptions.PersonNotFoundException;
import ru.isaykin.app.mappers.NoteMapper;
import ru.isaykin.app.repositories.NotesRepository;
import ru.isaykin.app.repositories.PersonsRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.isaykin.app.mappers.NoteMapper.*;

@Service
public class NotesService {

    private final NotesRepository notesRepository;
    private final PersonsRepository personsRepository;

    public NotesService(NotesRepository notesRepository, PersonsRepository personsRepository) {
        this.notesRepository = notesRepository;
        this.personsRepository = personsRepository;
    }

    public NoteDTO getNoteById(Long personId, Long noteId) {
        NoteDTO result;
        Person person = personsRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        List<NoteDTO> noteList = getListOfNotesFromTelephoneBook(person);
        try {

            result = noteList.get(noteId.intValue() - 1);
        } catch (IndexOutOfBoundsException exception) {
            throw new NoteNotFoundException("Note not found. Wrong id.");
        }
        return result;
    }

    private List<NoteDTO> getListOfNotesFromTelephoneBook(Person person) {
        List<NoteDTO> noteDTOList = new ArrayList<>();
        List<Note> noteList = List.copyOf(person.getTelephoneBook().getNotes());
        Long idNumber = 1L;
        for (int i = 0; i < noteList.size(); i++) {
            NoteDTO noteDTO = INSTANCE.fromNoteToNoteDTO(noteList.get(i));
            noteDTO.setId(idNumber);
            noteDTOList.add(noteDTO);
            idNumber++;
        }
        return noteDTOList;
    }

    public NoteDTO addNoteToPersonById(Long personId, NoteDTO noteDTO) {
        Person person = personsRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        Note note = INSTANCE.fromNoteDTOToNote(noteDTO);
        notesRepository.addNoteToPersonById(personId, note.getContactName(), note.getTelephoneNumber());
        NoteDTO result = getLastNoteOfPerson(personId);
        return result;
    }

    private NoteDTO getLastNoteOfPerson(Long personId) {
        List<NoteDTO> noteDTOList = getListOfNoteDTOById(personId);
        NoteDTO result = noteDTOList.get(noteDTOList.size() - 1);
        return result;
    }

    public List<NoteDTO> getListOfNoteDTOById(Long personId) {
        Person person = personsRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        List<NoteDTO> resultList = getListOfNotesFromTelephoneBook(person);
        return resultList;
    }

    public Long deleteNoteById(Long personId, Long noteDTOId) {
        NoteDTO noteDTO = getNoteDTOByPersonBookNoteId(personId, noteDTOId);

        Note noteToDelete = notesRepository.findByTelephoneNumber(noteDTO.getTelephoneNumber())
                .orElseThrow(() -> new NoteNotFoundException("Note not found. Wrong id."));
        Long noteId = noteToDelete.getNoteId();
        notesRepository.delete(noteToDelete);

        Long result = noteDTOId;
        if (notesRepository.existsById(noteId)) result = 0L;
        return result;
    }
    private NoteDTO getNoteDTOByPersonBookNoteId(Long personId, Long noteDTOId) {
        List<NoteDTO> noteDTOList = getListOfNoteDTOById(personId);
        if (noteDTOList.size() < noteDTOId) throw new NoteNotFoundException("Note not found. Wrong id.");
        return noteDTOList.get(noteDTOId.intValue() - 1);
    }
    public NoteDTO updateNoteInPersonBook(Long personId, Long noteDTOId, String contactName, String telephoneNumber) {
        NoteDTO noteDTO = getNoteDTOByPersonBookNoteId(personId, noteDTOId);

        Note noteToUpdate = notesRepository.findByTelephoneNumber(noteDTO.getTelephoneNumber())
                .orElseThrow(() -> new NoteNotFoundException("Note not found. Wrong id."));

        if(contactName != null) noteToUpdate.setContactName(contactName);
        if(telephoneNumber !=null) {
            telephoneNumber = addPluToTelephoneNumber(telephoneNumber);
            noteToUpdate.setTelephoneNumber(telephoneNumber);}
        noteToUpdate = notesRepository.save(noteToUpdate);

        noteDTO = INSTANCE.fromNoteToNoteDTO(noteToUpdate);
        noteDTO.setId(noteDTOId);
        return noteDTO;
    }

    private String addPluToTelephoneNumber(String telephoneNumber) {
      return telephoneNumber.replace(" ", "+");
    }
}

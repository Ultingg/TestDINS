package ru.isaykin.app.services;

import org.springframework.stereotype.Service;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.exceptions.InvalidNoteException;
import ru.isaykin.app.exceptions.NoteNotFoundException;
import ru.isaykin.app.exceptions.PersonNotFoundException;
import ru.isaykin.app.repositories.NotesRepository;
import ru.isaykin.app.repositories.PersonsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.isaykin.app.mappers.NoteMapper.INSTANCE;

@SuppressWarnings("UnnecessaryLocalVariable")
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
        } catch (IndexOutOfBoundsException e) {
            throw new NoteNotFoundException("Note not found. Wrong id.");
        }catch (NullPointerException exception){
            throw new InvalidNoteException("You send invalid person for update.");
        }
        return result;
    }

    private List<NoteDTO> getListOfNotesFromTelephoneBook(Person person) {
        List<NoteDTO> noteDTOList = new ArrayList<>();
        List<Note> noteList = List.copyOf(person.getTelephoneBook().getNotes());
        Long idNumber = 1L;
        for (Note note : noteList) {
            NoteDTO noteDTO = INSTANCE.fromNoteToNoteDTO(note);
            noteDTO.setId(idNumber);
            noteDTOList.add(noteDTO);
            idNumber++;
        }
        return noteDTOList;
    }

    public NoteDTO getNoteFromPersonBookByTelephoneNumber(Long personId, String telephoneNumber) {
        List<NoteDTO> noteDTOList = getListOfNoteDTOById(personId);
        String telephone = addPlusToTelephoneNumber(telephoneNumber);
        NoteDTO noteDTO = noteDTOList.stream()
                .filter(noteDTO1 -> noteDTO1.getTelephoneNumber().equals(telephone))
                .findAny().orElseThrow(() -> new NoteNotFoundException("Note not found. Wrong id."));
        return noteDTO;
    }

    public NoteDTO addNoteToPersonById(Long personId, NoteDTO noteDTO) {
        Person person = personsRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found. Wrong id."));
        if(noteDTO == null) throw new InvalidNoteException("You send invalid person for update.");
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

    public Map<String, Object> deleteNoteByIdFromPersonBook(Long personId, Long noteDTOId) {
        Map<String, Object> resultMap = new HashMap<>();
        NoteDTO noteDTO = getNoteDTOByPersonBookNoteId(personId, noteDTOId);
        Note noteToDelete = notesRepository.findByTelephoneNumber(noteDTO.getTelephoneNumber())
                .orElseThrow(() -> new NoteNotFoundException("Note not found. Wrong id."));
        notesRepository.delete(noteToDelete);
        resultMap.put("Message: ", "Note was deleted");
        resultMap.put("Note: ", noteDTO);
        return resultMap;
    }

    private NoteDTO getNoteDTOByPersonBookNoteId(Long personId, Long noteDTOId) {
        List<NoteDTO> noteDTOList = getListOfNoteDTOById(personId);
        if (noteDTOList.size() < noteDTOId) throw new NoteNotFoundException("Note not found. Wrong id.");
        NoteDTO noteDTOResult = noteDTOList.get(noteDTOId.intValue() - 1);
        return noteDTOResult;
    }

    public NoteDTO updateNoteInPersonBook(Long personId, Long noteDTOId, String contactName, String telephoneNumber) {
        NoteDTO noteDTO = getNoteDTOByPersonBookNoteId(personId, noteDTOId);

        Note noteToUpdate = notesRepository.findByTelephoneNumber(noteDTO.getTelephoneNumber())
                .orElseThrow(() -> new NoteNotFoundException("Note not found. Wrong id."));

        if (contactName != null) noteToUpdate.setContactName(contactName);
        if (telephoneNumber != null) {
            telephoneNumber = addPlusToTelephoneNumber(telephoneNumber);
            noteToUpdate.setTelephoneNumber(telephoneNumber);
        }
        noteToUpdate = notesRepository.save(noteToUpdate);

        noteDTO = INSTANCE.fromNoteToNoteDTO(noteToUpdate);
        noteDTO.setId(noteDTOId);
        return noteDTO;
    }

    private String addPlusToTelephoneNumber(String telephoneNumber) {
        return telephoneNumber.replace(" ", "+");
    }
}

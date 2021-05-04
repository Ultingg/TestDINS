package ru.isaykin.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.entities.Note;

@Mapper
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mappings({
            @Mapping(source = "noteDTO.id", target = "noteId"),
            @Mapping(source = "noteDTO.contactName", target = "contactName"),
            @Mapping(source = "noteDTO.telephoneNumber", target = "telephoneNumber")})
    Note fromNoteDTOToNote(NoteDTO noteDTO);

    @Mappings({
            @Mapping(source = "note.noteId", target = "id"),
            @Mapping(source = "note.contactName", target = "contactName"),
            @Mapping(source = "note.telephoneNumber", target = "telephoneNumber")})
    NoteDTO fromNoteToNoteDTO(Note note);


}

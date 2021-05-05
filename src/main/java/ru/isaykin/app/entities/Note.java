package ru.isaykin.app.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Data
@Table("notes")
public class Note {

    @Id
    private Long noteId;
    private String contactName;
    private String telephoneNumber;

    public Note(String contactName, String telephoneNumber) {
        this.contactName = contactName;
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        Note note = (Note) o;
        return Objects.equals(noteId, note.noteId) &&
                Objects.equals(contactName, note.contactName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, contactName);
    }
}

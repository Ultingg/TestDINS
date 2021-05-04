package ru.isaykin.app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@Table("telephone_books")
//@NoArgsConstructor
public class TelephoneBook {
    @Id
    private Long telephoneBookId;
    @MappedCollection(idColumn = "telephone_book_id")
    private Set<Note> notes;

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public TelephoneBook() {
        this.notes = Set.of();
    }

    public TelephoneBook(Set<Note> notes) {
        this.notes = notes;
    }
}

package ru.isaykin.app.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
}

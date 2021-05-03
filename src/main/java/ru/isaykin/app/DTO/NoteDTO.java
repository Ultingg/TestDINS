package ru.isaykin.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NoteDTO {

    private Long id;
    private String contactName;
    private String telephoneNumber;

    public NoteDTO(String contactName, String telephoneNumber) {
        this.contactName = contactName;
        this.telephoneNumber = telephoneNumber;
    }
}

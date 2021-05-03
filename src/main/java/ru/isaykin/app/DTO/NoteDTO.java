package ru.isaykin.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class NoteDTO {

    private Long id;
    private String contactName;
    private String telephoneNumber;

    public NoteDTO() {}

    public NoteDTO(String contactName, String telephoneNumber) {
        this.contactName = contactName;
        this.telephoneNumber = telephoneNumber;
    }

    public NoteDTO(Long id, String contactName, String telephoneNumber) {
        this.id = id;
        this.contactName = contactName;
        this.telephoneNumber = telephoneNumber;
    }
}

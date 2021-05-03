package ru.isaykin.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@Component
public class NoteDTO {

    private Long id;
    @Size(min = 1, max = 80, message = "Incorrect name. Min size = 1, Max size = 80")
    @Pattern(regexp = "[\\s?[a-zA-Zа-яА-я]*\\s?]{1,80}", message = "Incorrect name. Use only letters.")
    private String contactName;
    @Pattern(regexp = "(\\+)[0-9]{11,13}", message = "Incorrect phone number. Use this format +70123456789 or +7012345678910.")
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

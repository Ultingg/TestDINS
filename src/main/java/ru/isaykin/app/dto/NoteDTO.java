package ru.isaykin.app.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

import static java.util.Objects.*;

@Data
@Builder
@Component
public class NoteDTO {

    private Long id;

    @Size(min = 1, max = 80, message = "Incorrect name. Min size = 1, Max size = 80")
    @Pattern(regexp = "[\\s?[a-zA-Zа-яА-я]]{1,80}", message = "Incorrect name. Use only letters.")
    private String contactName;

    @Size(min = 12, max = 12, message = "Incorrect phone number. Correct size = 12")
    @Pattern(regexp = "(\\+)[0-9]{11,12}", message = "Incorrect phone number. Use this format +70123456789.")
    private String telephoneNumber;

    public NoteDTO() {
    }

    public NoteDTO(String contactName, String telephoneNumber) {
        this.contactName = contactName;
        this.telephoneNumber = telephoneNumber;
    }

    public NoteDTO(Long id, String contactName, String telephoneNumber) {
        this.id = id;
        this.contactName = contactName;
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteDTO noteDTO = (NoteDTO) o;
        return Objects.equals(id, noteDTO.id) &&
                Objects.equals(contactName, noteDTO.contactName);
    }

    @Override
    public int hashCode() {
        return hash(id, contactName);
    }
}

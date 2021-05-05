package ru.isaykin.app.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Component
public class PersonDTO {

    private Long id;

    @Size(min = 1, max = 80, message = "Incorrect name. Min size = 1, Max size = 80")
    @Pattern(regexp = "[\\s?[a-zA-Zа-яА-я]]{1,80}", message = "Incorrect name. Use only letters.")
    private String firstName;

    @Size(min = 1, max = 80, message = "Incorrect name. Min size = 1, Max size = 80")
    @Pattern(regexp = "[\\s?[a-zA-Zа-яА-я]]{1,80}", message = "Incorrect name. Use only letters.")
    private String lastName;
}

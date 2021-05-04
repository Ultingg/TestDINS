import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.NoteDTO;
import ru.isaykin.app.dto.PersonDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTests {


    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void firstNamePerson_validConstrain() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("Jhon");
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNamePerson_invalidConstrain() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("23123");
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void lastNamePersonDTO_validConstrain() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setLastName("Stone");
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNamePersonDTO_invalidConstrain() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setLastName("23123");
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void contactNameNoteDTO_validConstrain() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setContactName("Leon");
        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(noteDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void contactNameNoteDTO_invalidConstrain() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setContactName("Par897)");
        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(noteDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void telephoneNumberNoteDTO_validConstrain() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTelephoneNumber("+19214897899");
        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(noteDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void telephoneNumberNoteDTO_invalidConstrain() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTelephoneNumber("23df2123");
        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(noteDTO);

        assertFalse(violations.isEmpty());
    }


}

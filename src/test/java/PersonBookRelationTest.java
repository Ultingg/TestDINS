import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import ru.isaykin.app.Application;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.repositories.PersonsRepository;

import static java.util.Set.of;

@SpringBootTest(classes = Application.class)
@ComponentScan(value = "ru.isaykin.app")
public class PersonBookRelationTest {

    @Autowired
    private PersonsRepository personsRepository;


    @Test
    public void savingPerson() {


        Note note = new Note("Vova", "+79851116");
        Note noteK = new Note("Karas", "+7925827");
        Note noteB = new Note("Boris", "+7125622587");
        TelephoneBook telephoneBook = new TelephoneBook(of(note, noteK, noteB));

        Person pepe = new Person("Pepe","Rimlyanin", telephoneBook);

        personsRepository.save(pepe);

        Person pers  = personsRepository.findById(1L).orElseThrow();

        System.out.println("============");

        var notes = pers.getTelephoneBook().getNotes();

        for(Note n : notes) System.out.println("====" + n);



    }
}


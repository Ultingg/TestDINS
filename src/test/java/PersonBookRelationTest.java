import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import ru.isaykin.app.Application;
import ru.isaykin.app.entities.Note;
import ru.isaykin.app.entities.Person;
import ru.isaykin.app.entities.TelephoneBook;
import ru.isaykin.app.repositories.PersonsRepository;

import java.util.HashSet;

import static java.util.Set.*;

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
        Person person = new Person( "Petr", "Ivshin", telephoneBook);

        TelephoneBook telephoneBook2 = new TelephoneBook(of(
                new Note("Pushkin", "+7995622587"),
                new Note("Griboedov", "+7885622587"),
                new Note("Gogol", "+7775622587")));
        Person person1 = new Person( "Lev", "Tolst", telephoneBook2);

        TelephoneBook telephoneBook3 = new TelephoneBook(of(
                new Note("Petr", "+112323123"),
                new Note("Ieshua", "+44444444"),
                new Note("Pontiy", "+7878788")));
        Person person2 = new Person( "David", "Goliaph", telephoneBook3);

        Note pavelDad = new Note("Daddy", "+7899545221");
        Note pavelMom = new Note("Mommy", "+7898975220");
        Note pavelBrother = new Note("Brother", "+7815236974");

        TelephoneBook pavelBook = new TelephoneBook(of(pavelDad, pavelBrother, pavelMom));
        Person sheff = new Person( "Pavel", "Druzk", pavelBook);

        Note Lusy = new Note("Lusy", "+79125521");
        Note Vera = new Note("Vera", "+7879564362");
        Note Tema = new Note("Artem", "+79115236974");

        TelephoneBook ryabaBook = new TelephoneBook(of(Lusy, Vera, Tema));
        Person drug = new Person( "Sveta", "Ryaba", ryabaBook);

        Person pepe = new Person("Pepe","Rimlyanin", new TelephoneBook());


        personsRepository.save(person);
        personsRepository.save(person1);
        personsRepository.save(person2);
        personsRepository.save(sheff);
        personsRepository.save(drug);
        personsRepository.save(pepe);
//        System.out.println(personsRepository.findAll());

        System.out.println(personsRepository.findById(1L));
        Person pers  = personsRepository.findById(2L).orElseThrow();

        System.out.println("============");

        var notes = pers.getTelephoneBook().getNotes();

        for(Note n : notes) System.out.println("====" + n);

//        ArrayList<Person> persons =new ArrayList<>();
//        personsRepository.findAll().iterator().forEachRemaining(persons::add);
//
//        for (Person per : persons) {
//
//            System.out.println("=================");
//            System.out.println(per.getFirstName());
//            for (Note not : per.getTelephoneBook().getNotes()) {
//                System.out.println(not);
//            }
//        }

    }
}


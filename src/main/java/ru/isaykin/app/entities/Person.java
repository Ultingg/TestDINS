package ru.isaykin.app.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("persons")
public class Person {

    @Id
    private Long personId;
    private String firstName;
    private String lastName;
    @MappedCollection(idColumn = "person_id")
    private TelephoneBook telephoneBook;

    public Person(String firstName, String lastName, TelephoneBook telephoneBook) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneBook = telephoneBook;
    }


}

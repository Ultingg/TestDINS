package ru.isaykin.app.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isaykin.app.entities.Note;

import java.util.Optional;

@Repository
public interface NotesRepository extends CrudRepository<Note, Long> {

    @Query(
            "INSERT INTO notes (telephone_book_id, contact_name, telephone_number) VALUES (:personId, :contactName,:telephoneNumber);")
    @Modifying
    void addNoteToPersonById(@Param("personId") Long personId,
                             @Param("contactName") String contactName,
                             @Param("telephoneNumber") String telephoneNumber);


    Optional<Note> findByTelephoneNumber(String telephoneNumber);
}

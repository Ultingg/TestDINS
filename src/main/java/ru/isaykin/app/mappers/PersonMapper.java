package ru.isaykin.app.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.isaykin.app.dto.PersonDTO;
import ru.isaykin.app.entities.Person;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mappings({
            @Mapping(source = "personDTO.id", target = "personId"),
            @Mapping(source = "personDTO.firstName", target = "firstName"),
            @Mapping(source = "personDTO.lastName", target = "lastName")})
    Person fromPersonDTOToPerson(PersonDTO personDTO);

    @Mappings({
            @Mapping(source = "person.personId", target = "id"),
            @Mapping(source = "person.firstName", target = "firstName"),
            @Mapping(source = "person.lastName", target = "lastName")})
    PersonDTO fromPersonToPersonDTO(Person person);
}

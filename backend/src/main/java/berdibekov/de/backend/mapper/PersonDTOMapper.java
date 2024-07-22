package berdibekov.de.backend.mapper;

import berdibekov.de.backend.dto.PersonDTO;
import berdibekov.de.backend.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDTOMapper {

    PersonDTO toDTO(Person person, String personId);

    Person toModel(PersonDTO personDTO);

}

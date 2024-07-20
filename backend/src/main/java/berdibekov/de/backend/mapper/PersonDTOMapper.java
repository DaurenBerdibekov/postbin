package berdibekov.de.backend.mapper;

import berdibekov.de.backend.dto.PersonDTO;
import berdibekov.de.backend.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDTOMapper {

    PersonDTO toDTO(Person person);

    Person toModel(PersonDTO personDTO);

}

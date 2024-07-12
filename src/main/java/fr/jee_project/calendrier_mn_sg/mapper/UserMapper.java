package fr.jee_project.calendrier_mn_sg.mapper;

import fr.jee_project.calendrier_mn_sg.business.User;
import fr.jee_project.calendrier_mn_sg.dto.UserDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {
    User toEntity(UserDto utilisateurDto);

    UserDto toDto(User utilisateur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto utilisateurDto,
                       @MappingTarget User utilisateur);
}

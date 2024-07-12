package fr.jee_project.calendrier_mn_sg.mapper;

import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.dto.UserDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {
    UserCal toEntity(UserDto utilisateurDto);

    UserDto toDto(UserCal utilisateur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCal partialUpdate(UserDto utilisateurDto,
                       @MappingTarget UserCal utilisateur);
}

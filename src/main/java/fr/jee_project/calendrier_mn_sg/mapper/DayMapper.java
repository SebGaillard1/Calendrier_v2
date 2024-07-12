package fr.jee_project.calendrier_mn_sg.mapper;

import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.dto.DayDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface DayMapper {
    DayCal toEntity(DayDto jourDto);

    DayDto toDto(DayCal jour);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DayCal partialUpdate(DayDto jourDto,
                      @MappingTarget DayCal jour);
}

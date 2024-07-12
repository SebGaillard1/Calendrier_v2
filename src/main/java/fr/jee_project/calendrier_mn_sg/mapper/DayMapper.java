package fr.jee_project.calendrier_mn_sg.mapper;

import fr.jee_project.calendrier_mn_sg.business.Day;
import fr.jee_project.calendrier_mn_sg.dto.DayDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface DayMapper {
    Day toEntity(DayDto jourDto);

    DayDto toDto(Day jour);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Day partialUpdate(DayDto jourDto,
                      @MappingTarget Day jour);
}

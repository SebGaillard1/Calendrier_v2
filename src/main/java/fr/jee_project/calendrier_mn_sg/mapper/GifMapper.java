package fr.jee_project.calendrier_mn_sg.mapper;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.dto.GifDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface GifMapper {
    Gif toEntity(GifDto gifDto);

    GifDto toDto(Gif gif);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Gif partialUpdate(GifDto gifDto,
                      @MappingTarget Gif gif);
}

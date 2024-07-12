package fr.jee_project.calendrier_mn_sg.controller.rest;

import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import fr.jee_project.calendrier_mn_sg.dto.DayDto;
import fr.jee_project.calendrier_mn_sg.dto.UpdatedDayDto;
import fr.jee_project.calendrier_mn_sg.mapper.DayMapper;
import fr.jee_project.calendrier_mn_sg.service.DayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/days")
@AllArgsConstructor
public class DayRestController {

    private final DayService dayService;
    private final DayMapper dayMapper;

    @GetMapping("")
    @Operation(summary = "Get all days")
    public Page<DayCal> getAllDays(@PageableDefault() Pageable pageable) {
        return dayService.findAll(pageable);
    }

    @GetMapping("/{day}/{mois}")
    @Operation(summary = "Get day by id")
    public DayCal getDayById(@PathVariable String day, @PathVariable String mois) {
        IdDay dayId = new IdDay(Integer.parseInt(day), Integer.parseInt(mois));
        return dayService.findById(dayId);
    }

    @PostMapping("")
    @Operation(summary = "Create a new day", responses = {@ApiResponse(responseCode = "201", description = "Ajout ok")})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createDay(@RequestBody DayDto dayDto) {
        dayService.save(dayMapper.toEntity(dayDto));
    }

    @PutMapping("/{day}/{mois}")
    @Operation(summary = "Update day by id")
    public void updateDay(@PathVariable String day, @PathVariable String mois, @RequestBody UpdatedDayDto dayUpdateDto) {
        IdDay id = new IdDay(Integer.parseInt(day), Integer.parseInt(mois));
        DayCal dayToUpdate = dayService.findById(id);
        dayToUpdate.setGif(dayUpdateDto.getGif());
        dayToUpdate.setPoints(dayUpdateDto.getPoints());
        dayToUpdate.setUser(dayUpdateDto.getUtilisateur());
        dayService.save(dayToUpdate);
    }

    @DeleteMapping("/{day}/{mois}")
    @Operation(summary = "Delete day by id")
    public void deleteDay(@PathVariable String day, @PathVariable String mois) {
        IdDay id = new IdDay(Integer.parseInt(day), Integer.parseInt(mois));
        DayCal dayToDelete = dayService.findById(id);
        dayService.delete(dayToDelete);
    }
}


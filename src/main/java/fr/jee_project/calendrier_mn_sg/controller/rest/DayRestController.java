package fr.jee_project.calendrier_mn_sg.controller.rest;

import fr.jee_project.calendrier_mn_sg.business.Day;
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
@RequestMapping("api/jours")
@AllArgsConstructor
public class DayRestController {

    private final DayService jourService;
    private final DayMapper jourMapper;

    @GetMapping("")
    @Operation(summary = "Get all jours")
    public Page<Day> getAllJours(@PageableDefault() Pageable pageable) {
        return jourService.findAll(pageable);
    }

    @GetMapping("/{jour}/{mois}")
    @Operation(summary = "Get jour by id")
    public Day getJourById(@PathVariable String jour, @PathVariable String mois) {
        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        return jourService.findById(jourId);
    }

    @PostMapping("")
    @Operation(summary = "Create a new jour", responses = {@ApiResponse(responseCode = "201", description = "Ajout ok")})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createJour(@RequestBody DayDto jourDto) {
        jourService.save(jourMapper.toEntity(jourDto));
    }

    @PutMapping("/{jour}/{mois}")
    @Operation(summary = "Update jour by id")
    public void updateJour(@PathVariable String jour, @PathVariable String mois, @RequestBody UpdatedDayDto jourUpdateDto) {
        IdDay id = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        Day jourToUpdate = jourService.findById(id);
        jourToUpdate.setGif(jourUpdateDto.getGif());
        jourToUpdate.setPoints(jourUpdateDto.getPoints());
        jourToUpdate.setUtilisateur(jourUpdateDto.getUtilisateur());
        jourService.save(jourToUpdate);
    }

    @DeleteMapping("/{jour}/{mois}")
    @Operation(summary = "Delete jour by id")
    public void deleteJour(@PathVariable String jour, @PathVariable String mois) {
        IdDay id = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        Day jourToDelete = jourService.findById(id);
        jourService.delete(jourToDelete);
    }
}


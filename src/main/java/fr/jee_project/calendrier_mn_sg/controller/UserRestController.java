package fr.jee_project.calendrier_mn_sg.controller;

import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.dto.UserDto;
import fr.jee_project.calendrier_mn_sg.mapper.UserMapper;
import fr.jee_project.calendrier_mn_sg.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/utilisateurs")
@AllArgsConstructor
public class UserRestController {

    private UserService utilisateurService;
    private UserMapper utilisateurMapper;

    @GetMapping("")
    @Operation(summary = "Get all utilisateurs")
    public Page<UserCal> getAllUtilisateurs(@PageableDefault() Pageable pageable) {
        return utilisateurService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get utilisateur by id")
    public UserCal getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.findById(id);
    }

    @PostMapping("")
    @Operation(summary = "Create a new utilisateur", responses = {@ApiResponse(responseCode = "201", description = "Ajout ok")})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUtilisateur(
            @Valid @RequestBody UserDto utilisateur
    ) {
        utilisateurService.save(utilisateurMapper.toEntity(utilisateur));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update utilisateur by id")
    public void updateUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody UserDto utilisateur
    ) {
        UserCal utilisateurToUpdate = utilisateurService.findById(id);
        utilisateurToUpdate.setNom(utilisateur.getNom());
        utilisateurToUpdate.setPrenom(utilisateur.getPrenom());
        utilisateurToUpdate.setEmail(utilisateur.getEmail());
        utilisateurToUpdate.setTheme(utilisateur.getTheme());
        utilisateurService.save(utilisateurToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete utilisateur by id")
    public void deleteUtilisateur(@PathVariable Long id) {
        UserCal utilisateur = utilisateurService.findById(id);
        utilisateurService.delete(utilisateur);
    }
}

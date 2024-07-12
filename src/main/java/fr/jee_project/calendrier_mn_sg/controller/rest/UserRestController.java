package fr.jee_project.calendrier_mn_sg.controller.rest;

import fr.jee_project.calendrier_mn_sg.business.User;
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
@RequestMapping("api/users")
@AllArgsConstructor
public class UserRestController {

    private UserService userService;
    private UserMapper userMapper;

    @GetMapping("")
    @Operation(summary = "Get all users")
    public Page<User> getAllusers(@PageableDefault() Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public User getuserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("")
    @Operation(summary = "Create a new user", responses = {@ApiResponse(responseCode = "201", description = "Ajout ok")})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createuser(
            @Valid @RequestBody UserDto user
    ) {
        userService.save(userMapper.toEntity(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by id")
    public void updateuser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto user
    ) {
        User userToUpdate = userService.findById(id);
        userToUpdate.setNom(user.getNom());
        userToUpdate.setPrenom(user.getPrenom());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setTheme(user.getTheme());
        userService.save(userToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    public void deleteuser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(user);
    }
}

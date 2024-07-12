package fr.jee_project.calendrier_mn_sg.controller.rest;

import fr.jee_project.calendrier_mn_sg.business.User;
import fr.jee_project.calendrier_mn_sg.dto.UserDto;
import fr.jee_project.calendrier_mn_sg.mapper.UserMapper;
import fr.jee_project.calendrier_mn_sg.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserRestControllerUnitTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    UserService userService;
    @MockBean
    UserMapper userMapper;

    @Test
    void testGetAllusers() throws Exception {
        User user = new User();
        Page<User> users = new PageImpl<>(Arrays.asList(user));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(userService.findAll(pageableCaptor.capture())).thenReturn(users);

        mvc.perform(get("/api/users?page=0&size=10"))
                .andExpect(status().isOk());

        verify(userService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetuserById() throws Exception {
        User user = new User();
        when(userService.findById(anyLong())).thenReturn(user);

        mvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).findById(anyLong());
    }

    @Test
    void testCreateuser() throws Exception {
        User user = new User();

        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "nom":"John",
                                   "prenom":"Doe",
                                   "email":"john.doe@esgi.fr",
                                   "theme":"dark",
                                   "password":"password"
                                 }
                                """))
                .andExpect(status().isCreated());

        verify(userService, times(1)).save(any(User.class));
    }


    @Test
    void testUpdateuser() throws Exception {
        User user = new User();
        when(userService.findById(anyLong())).thenReturn(user);

        mvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                    "nom":"John",
                                    "prenom":"Doe",
                                    "email":"john.doe@esgi.fr",
                                    "theme":"dark",
                                    "password":"password"
                                  }
                                """))
                .andExpect(status().isOk());

        verify(userService, times(1)).findById(anyLong());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteuser() throws Exception {
        User user = new User();
        when(userService.findById(anyLong())).thenReturn(user);

        mvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).findById(anyLong());
        verify(userService, times(1)).delete(any(User.class));
    }
}

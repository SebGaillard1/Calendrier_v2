package fr.jee_project.calendrier_mn_sg.controller.rest;

import fr.jee_project.calendrier_mn_sg.business.UserCal;
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
class UtilisateurRestControllerUnitTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    UserService utilisateurService;
    @MockBean
    UserMapper utilisateurMapper;

    @Test
    void testGetAllUtilisateurs() throws Exception {
        UserCal utilisateur = new UserCal();
        Page<UserCal> utilisateurs = new PageImpl<>(Arrays.asList(utilisateur));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(utilisateurService.findAll(pageableCaptor.capture())).thenReturn(utilisateurs);

        mvc.perform(get("/api/utilisateurs?page=0&size=10"))
                .andExpect(status().isOk());

        verify(utilisateurService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetUtilisateurById() throws Exception {
        UserCal utilisateur = new UserCal();
        when(utilisateurService.findById(anyLong())).thenReturn(utilisateur);

        mvc.perform(get("/api/utilisateurs/1"))
                .andExpect(status().isOk());

        verify(utilisateurService, times(1)).findById(anyLong());
    }

    @Test
    void testCreateUtilisateur() throws Exception {
        UserCal utilisateur = new UserCal();

        when(utilisateurMapper.toEntity(any(UserDto.class))).thenReturn(utilisateur);

        mvc.perform(post("/api/utilisateurs")
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

        verify(utilisateurService, times(1)).save(any(UserCal.class));
    }


    @Test
    void testUpdateUtilisateur() throws Exception {
        UserCal utilisateur = new UserCal();
        when(utilisateurService.findById(anyLong())).thenReturn(utilisateur);

        mvc.perform(put("/api/utilisateurs/1")
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

        verify(utilisateurService, times(1)).findById(anyLong());
        verify(utilisateurService, times(1)).save(any(UserCal.class));
    }

    @Test
    void testDeleteUtilisateur() throws Exception {
        UserCal utilisateur = new UserCal();
        when(utilisateurService.findById(anyLong())).thenReturn(utilisateur);

        mvc.perform(delete("/api/utilisateurs/1"))
                .andExpect(status().isOk());

        verify(utilisateurService, times(1)).findById(anyLong());
        verify(utilisateurService, times(1)).delete(any(UserCal.class));
    }
}

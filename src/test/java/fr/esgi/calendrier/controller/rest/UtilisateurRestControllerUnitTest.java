package fr.esgi.calendrier.controller.rest;

import fr.esgi.calendrier.business.Utilisateur;
import fr.esgi.calendrier.dto.UtilisateurDto;
import fr.esgi.calendrier.mapper.UtilisateurMapper;
import fr.esgi.calendrier.service.UtilisateurService;
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
    UtilisateurService utilisateurService;
    @MockBean
    UtilisateurMapper utilisateurMapper;

    @Test
    void testGetAllUtilisateurs() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        Page<Utilisateur> utilisateurs = new PageImpl<>(Arrays.asList(utilisateur));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(utilisateurService.findAll(pageableCaptor.capture())).thenReturn(utilisateurs);

        mvc.perform(get("/api/utilisateurs?page=0&size=10"))
                .andExpect(status().isOk());

        verify(utilisateurService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetUtilisateurById() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        when(utilisateurService.findById(anyLong())).thenReturn(utilisateur);

        mvc.perform(get("/api/utilisateurs/1"))
                .andExpect(status().isOk());

        verify(utilisateurService, times(1)).findById(anyLong());
    }

    @Test
    void testCreateUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();

        when(utilisateurMapper.toEntity(any(UtilisateurDto.class))).thenReturn(utilisateur);

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

        verify(utilisateurService, times(1)).save(any(Utilisateur.class));
    }


    @Test
    void testUpdateUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
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
        verify(utilisateurService, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void testDeleteUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        when(utilisateurService.findById(anyLong())).thenReturn(utilisateur);

        mvc.perform(delete("/api/utilisateurs/1"))
                .andExpect(status().isOk());

        verify(utilisateurService, times(1)).findById(anyLong());
        verify(utilisateurService, times(1)).delete(any(Utilisateur.class));
    }
}

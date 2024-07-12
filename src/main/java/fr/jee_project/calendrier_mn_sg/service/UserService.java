package fr.jee_project.calendrier_mn_sg.service;

import fr.jee_project.calendrier_mn_sg.business.UserCal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Page<UserCal> findAll(Pageable pageable);

    UserCal findByEmail(String email);

    UserCal findById(Long id);

    void save(UserCal utilisateur);

    void delete(UserCal utilisateur);

    UserCal subractPoints(UserCal utilisateur, int points);

    UserCal utilisateurFromSecurityContext(SecurityContext securityContext);
}

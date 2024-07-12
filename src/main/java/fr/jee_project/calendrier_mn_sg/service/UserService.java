package fr.jee_project.calendrier_mn_sg.service;

import fr.jee_project.calendrier_mn_sg.business.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);

    User findById(Long id);

    void save(User utilisateur);

    void delete(User utilisateur);

    User subractPoints(User utilisateur, int points);

    User userFromSecurityContext(SecurityContext securityContext);
}

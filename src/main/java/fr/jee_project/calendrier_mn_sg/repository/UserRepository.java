package fr.jee_project.calendrier_mn_sg.repository;

import fr.jee_project.calendrier_mn_sg.business.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}

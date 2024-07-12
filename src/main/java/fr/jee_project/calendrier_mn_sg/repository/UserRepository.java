package fr.jee_project.calendrier_mn_sg.repository;

import fr.jee_project.calendrier_mn_sg.business.UserCal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserCal, Long> {
    UserCal findByEmail(String username);
}

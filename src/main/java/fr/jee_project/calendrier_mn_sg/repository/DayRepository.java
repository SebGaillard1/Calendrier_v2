package fr.jee_project.calendrier_mn_sg.repository;

import fr.jee_project.calendrier_mn_sg.business.Day;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, IdDay> {
}

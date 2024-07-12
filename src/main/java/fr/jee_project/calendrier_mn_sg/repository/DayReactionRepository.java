package fr.jee_project.calendrier_mn_sg.repository;

import fr.jee_project.calendrier_mn_sg.business.DayReaction;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDayReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayReactionRepository extends JpaRepository<DayReaction, IdDayReaction> {
}

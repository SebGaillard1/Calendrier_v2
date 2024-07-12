package fr.jee_project.calendrier_mn_sg.service;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.Day;
import fr.jee_project.calendrier_mn_sg.business.User;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DayService {
    void save(Day jour);

    void setGif(IdDay id, Gif gif, User utilisateur);

    Page<Day> findAll(Pageable pageable);

    Day findById(IdDay id);

    void delete(Day jour);
}

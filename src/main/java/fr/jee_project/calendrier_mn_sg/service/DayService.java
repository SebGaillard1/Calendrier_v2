package fr.jee_project.calendrier_mn_sg.service;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DayService {
    void save(DayCal jour);

    void setGif(IdDay id, Gif gif, UserCal utilisateur);

    Page<DayCal> findAll(Pageable pageable);

    DayCal findById(IdDay id);

    void delete(DayCal jour);
}

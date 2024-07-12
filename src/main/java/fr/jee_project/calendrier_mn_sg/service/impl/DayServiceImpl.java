package fr.jee_project.calendrier_mn_sg.service.impl;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import fr.jee_project.calendrier_mn_sg.repository.DayRepository;
import fr.jee_project.calendrier_mn_sg.service.DayService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository dayRepository;

    @Override
    public void save(DayCal day) {
        this.dayRepository.save(day);
    }

    @Override
    public void setGif(IdDay id, Gif gif, UserCal utilisateur) {
        DayCal day = this.dayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Day not found"));
        day.setGif(gif);
        day.setUtilisateur(utilisateur);
        this.dayRepository.save(day);
    }

    @Override
    public Page<DayCal> findAll(Pageable pageable) {
        return this.dayRepository.findAll(pageable);
    }

    @Override
    public DayCal findById(IdDay id) {
        return this.dayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Day not found"));
    }

    @Override
    public void delete(DayCal day) {
        this.dayRepository.delete(day);
    }
}

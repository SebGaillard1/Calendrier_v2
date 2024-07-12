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
    private final DayRepository jourRepository;

    @Override
    public void save(DayCal jour) {
        this.jourRepository.save(jour);
    }

    @Override
    public void setGif(IdDay id, Gif gif, UserCal utilisateur) {
        DayCal jour = this.jourRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Day not found"));
        jour.setGif(gif);
        jour.setUtilisateur(utilisateur);
        this.jourRepository.save(jour);
    }

    @Override
    public Page<DayCal> findAll(Pageable pageable) {
        return this.jourRepository.findAll(pageable);
    }

    @Override
    public DayCal findById(IdDay id) {
        return this.jourRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Day not found"));
    }

    @Override
    public void delete(DayCal jour) {
        this.jourRepository.delete(jour);
    }
}

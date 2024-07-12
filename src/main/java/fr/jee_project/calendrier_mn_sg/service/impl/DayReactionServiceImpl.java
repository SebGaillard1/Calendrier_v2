package fr.jee_project.calendrier_mn_sg.service.impl;

import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.business.DayReaction;
import fr.jee_project.calendrier_mn_sg.business.User;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDayReaction;
import fr.jee_project.calendrier_mn_sg.repository.DayReactionRepository;
import fr.jee_project.calendrier_mn_sg.service.DayReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DayReactionServiceImpl implements DayReactionService {

    private final DayReactionRepository reactionJourRepository;

    @Override
    public void addOrRemoveReactionJour(IdDay jourId, ReactionType typeReaction, User utilisateur) {
        if (reactionJourRepository.existsById(new IdDayReaction(jourId, utilisateur.getId(), typeReaction.getId()))) {
            reactionJourRepository.deleteById(new IdDayReaction(jourId, utilisateur.getId(), typeReaction.getId()));
        } else {
            DayReaction reactionJour = new DayReaction();
            reactionJour.setId(new IdDayReaction(jourId, utilisateur.getId(), typeReaction.getId()));
            reactionJour.setUtilisateur(utilisateur);
            reactionJour.setTypeReaction(typeReaction);
            reactionJourRepository.save(reactionJour);
        }
    }
}

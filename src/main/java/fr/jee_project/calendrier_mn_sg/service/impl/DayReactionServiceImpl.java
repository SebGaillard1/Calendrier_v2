package fr.jee_project.calendrier_mn_sg.service.impl;

import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.business.DayReaction;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
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

    private final DayReactionRepository reactionDayRepository;

    @Override
    public void addOrRemoveReactionJour(IdDay dayId, ReactionType typeReaction, UserCal user) {
        if (reactionDayRepository.existsById(new IdDayReaction(dayId, user.getId(), typeReaction.getId()))) {
            reactionDayRepository.deleteById(new IdDayReaction(dayId, user.getId(), typeReaction.getId()));
        } else {
            DayReaction reactionday = new DayReaction();
            reactionday.setId(new IdDayReaction(dayId, user.getId(), typeReaction.getId()));
            reactionday.setUtilisateur(user);
            reactionday.setTypeReaction(typeReaction);
            reactionDayRepository.save(reactionday);
        }
    }
}

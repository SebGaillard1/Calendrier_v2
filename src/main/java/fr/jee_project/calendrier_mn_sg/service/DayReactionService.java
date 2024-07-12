package fr.jee_project.calendrier_mn_sg.service;

import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;

public interface DayReactionService {
    void addOrRemoveReactionJour(IdDay jourId, ReactionType typeReaction, UserCal utilisateur);
}

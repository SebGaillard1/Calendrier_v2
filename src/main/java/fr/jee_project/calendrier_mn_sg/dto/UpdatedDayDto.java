package fr.jee_project.calendrier_mn_sg.dto;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.DayReaction;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
import lombok.Value;

import java.util.List;

@Value
public class UpdatedDayDto {
    Gif gif;

    UserCal utilisateur;

    List<DayReaction> reactions;

    int points;
}

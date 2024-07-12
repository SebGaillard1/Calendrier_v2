package fr.jee_project.calendrier_mn_sg.dto;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.DayReaction;
import fr.jee_project.calendrier_mn_sg.business.User;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import lombok.Value;

import java.util.List;

@Value
public class DayDto {
    IdDay id;

    Gif gif;

    User utilisateur;

    List<DayReaction> reactions;

    int points;
}

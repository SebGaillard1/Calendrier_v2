package fr.jee_project.calendrier_mn_sg.service;

import fr.jee_project.calendrier_mn_sg.business.ReactionType;

import java.util.List;

public interface ReactionTypeService {
    void save(ReactionType typeReaction);

    ReactionType findById(Long id);

    List<ReactionType> findAll();
}

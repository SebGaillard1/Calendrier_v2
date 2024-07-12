package fr.jee_project.calendrier_mn_sg.service.impl;

import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.repository.ReactionTypeRepository;
import fr.jee_project.calendrier_mn_sg.service.ReactionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReactionTypeServiceImpl implements ReactionTypeService {
    private final ReactionTypeRepository typeReactionRepository;

    @Override
    public void save(ReactionType typeReaction) {
        this.typeReactionRepository.save(typeReaction);
    }

    @Override
    public ReactionType findById(Long id) {
        return this.typeReactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<ReactionType> findAll() {
        return this.typeReactionRepository.findAll();
    }
}

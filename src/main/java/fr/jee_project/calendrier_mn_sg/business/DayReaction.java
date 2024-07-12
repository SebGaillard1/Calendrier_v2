package fr.jee_project.calendrier_mn_sg.business;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reaction_jour")
public class DayReaction {
    @EmbeddedId
    private IdDayReaction id;

    @MapsId("utilisateurId")
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", insertable = false, updatable = false)
    private UserCal utilisateur;

    @MapsId("reactionId")
    @ManyToOne
    @JoinColumn(name = "reaction_id", insertable = false, updatable = false)
    private ReactionType typeReaction;
}

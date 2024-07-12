package fr.jee_project.calendrier_mn_sg.business;

import fr.jee_project.calendrier_mn_sg.business.Id.IdDayReaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reaction_day")
public class DayReaction {
    @EmbeddedId
    private IdDayReaction id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @MapsId("idReaction")
    @ManyToOne
    @JoinColumn(name = "reaction_id", insertable = false, updatable = false)
    private ReactionType typeReaction;
}

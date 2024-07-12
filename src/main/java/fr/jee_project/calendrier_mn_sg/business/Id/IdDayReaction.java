package fr.jee_project.calendrier_mn_sg.business.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdDayReaction implements Serializable {
    @Column(name = "jour_id")
    private IdDay jourId;
    @Column(name = "utilisateur_id")
    private Long utilisateurId;
    @Column(name = "reaction_id")
    private Long reactionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdDayReaction)) return false;
        IdDayReaction that = (IdDayReaction) o;
        return jourId.equals(that.jourId) && utilisateurId.equals(that.utilisateurId) && reactionId.equals(that.reactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jourId, utilisateurId, reactionId);
    }
}

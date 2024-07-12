package fr.jee_project.calendrier_mn_sg.business;

import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DayCal {
    @EmbeddedId
    @NonNull
    private IdDay id;

    @OneToOne()
    @Nullable()
    private Gif gif;

    @ManyToOne()
    @Nullable()
    private UserCal utilisateur;

    @OneToMany()
    @JoinColumn(name = "jour", referencedColumnName = "jour")
    @JoinColumn(name = "mois", referencedColumnName = "mois")
    private List<DayReaction> reactions = new ArrayList<>();

    @NonNull
    private int points;

    public String date() {
        String jour = String.valueOf(id.getJour());
        String mois = String.valueOf(id.getMois());

        if (jour.length() == 1) {
            jour = "0" + jour;
        }
        if (mois.length() == 1) {
            mois = "0" + mois;
        }

        return jour + "/" + mois;
    }

    public int getNbReactionByReaction(ReactionType typeReaction) {
        return (int) reactions.stream().filter(reactionJour -> reactionJour.getId().getReactionId().equals(typeReaction.getId())).count();
    }

    public List<String> getUsernamesByReaction(ReactionType typeReaction) {
        List<String> usernames = new ArrayList<>();
        reactions.stream().filter(
                reactionJour -> reactionJour.getId().getReactionId().equals(typeReaction.getId())
        ).forEach(
                reactionJour -> usernames.add(reactionJour.getUtilisateur().nom)
        );
        return usernames;
    }
}

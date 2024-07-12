package fr.jee_project.calendrier_mn_sg.business;

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
public class IdDay implements Serializable {
    private int jour;
    private int mois;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdDay jourId = (IdDay) o;
        return jour == jourId.jour && mois == jourId.mois;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jour, mois);
    }
}

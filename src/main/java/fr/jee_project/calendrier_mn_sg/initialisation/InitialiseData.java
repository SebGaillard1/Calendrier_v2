package fr.jee_project.calendrier_mn_sg.initialisation;

import fr.jee_project.calendrier_mn_sg.business.IdDay;
import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.service.DayService;
import fr.jee_project.calendrier_mn_sg.service.GifService;
import fr.jee_project.calendrier_mn_sg.service.ReactionTypeService;
import fr.jee_project.calendrier_mn_sg.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Component
@AllArgsConstructor
@Profile("!test")
public class InitialiseData implements CommandLineRunner {

    private final DayService jourService;
    private final ReactionTypeService typeReactionService;
    private final GifService gifService;
    private final UserService utilisateurService;

    private final Random random = new Random();

    @Override
    public void run(String... args) {
        ajoutUtilisateurParDefaut();
        deleteAllUploadingGif();
        ajoutDesReactions();
        ajoutDesJours();
    }

    private int getMonthDays(int month) {
        if (month == 1) {
            return 28;
        } else if (month == 3 || month == 5 || month == 8 || month == 10) {
            return 30;
        } else {
            return 31;
        }
    }
    private void deleteAllUploadingGif() {
        try {
            gifService.deleteAllUploadingGif();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ajoutDesReactions() {
        ArrayList<String> emojis = new ArrayList<>();
        emojis.add("\uD83D\uDE00");
        emojis.add("\uD83D\uDE02");
        emojis.add("\uD83D\uDE0D");
        emojis.add("\uD83D\uDE23");
        emojis.add("\uD83D\uDE21");

        for (String emoji : emojis) {
            ReactionType typeReaction = new ReactionType();
            typeReaction.setUnicode(emoji);
            typeReactionService.save(typeReaction);
        }
    }
    private void ajoutDesJours() {
        int mois = new Date().getMonth() + 1;

        for (int i = 1; i <= getMonthDays(mois); i++) {
            IdDay jourId = new IdDay(i, mois);
            int point = random.nextInt(100);
            DayCal jour = new DayCal(jourId, point);
            jourService.save(jour);
        }
    }



    private void ajoutUtilisateurParDefaut() {
        UserCal utilisateur = new UserCal();
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        utilisateur.setPassword("aaaaa");
        utilisateur.setEmail("a@esgi.fr");
        utilisateur.setTheme("light");
        utilisateurService.save(utilisateur);
    }


}

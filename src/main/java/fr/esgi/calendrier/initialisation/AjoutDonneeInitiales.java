package fr.esgi.calendrier.initialisation;

import fr.esgi.calendrier.business.*;
import fr.esgi.calendrier.business.customId.JourId;
import fr.esgi.calendrier.service.*;
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
public class AjoutDonneeInitiales implements CommandLineRunner {

    private final JourService jourService;
    private final TypeReactionService typeReactionService;
    private final GifService gifService;
    private final UtilisateurService utilisateurService;

    private final Random random = new Random();

    @Override
    public void run(String... args) {
        ajoutDesReactions();
        ajoutDesJours();
        ajoutUtilisateurParDefaut();
        deleteAllUploadingGif();
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

    private void ajoutDesJours() {
        int mois = new Date().getMonth() + 1;

        for (int i = 1; i <= getMonthDays(mois); i++) {
            JourId jourId = new JourId(i, mois);
            int point = random.nextInt(100);
            Jour jour = new Jour(jourId, point);
            jourService.save(jour);
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
            TypeReaction typeReaction = new TypeReaction();
            typeReaction.setUnicode(emoji);
            typeReactionService.save(typeReaction);
        }
    }

    private void ajoutUtilisateurParDefaut() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        utilisateur.setPassword("aaaaa");
        utilisateur.setEmail("a@esgi.fr");
        utilisateur.setTheme("light");
        utilisateurService.save(utilisateur);
    }

    private void deleteAllUploadingGif() {
        try {
            gifService.deleteAllUploadingGif();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

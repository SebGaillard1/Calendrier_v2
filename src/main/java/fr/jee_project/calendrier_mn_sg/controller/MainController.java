package fr.jee_project.calendrier_mn_sg.controller;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import fr.jee_project.calendrier_mn_sg.dto.UserDto;
import fr.jee_project.calendrier_mn_sg.mapper.GifMapper;
import fr.jee_project.calendrier_mn_sg.mapper.UserMapper;
import fr.jee_project.calendrier_mn_sg.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService utilisateurService;
    private final UserMapper utilisateurMapper;

    private final DayService jourService;
    private final GifService gifService;
    private final GifMapper gifMapper;
    private final ReactionTypeService typeReactionService;
    private final DayReactionService reactionJourService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid UserDto utilisateurDto) {
        utilisateurService.save(utilisateurMapper.toEntity(utilisateurDto));
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(
            Model model,
            @PageableDefault(size = 7) Pageable pageable
    ) {

        model.addAttribute("jours", jourService.findAll(pageable));
        model.addAttribute("typeReactions", typeReactionService.findAll());

        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        StringBuilder sortBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            sortBuilder.append(order.getProperty());
            sortBuilder.append(',');
            sortBuilder.append(order.getDirection());
            if (iterator.hasNext()) {
                // Bricodage
                sortBuilder.append("&sort=");
            }
        }
        model.addAttribute("sort", sortBuilder.toString());

        return "index";
    }

    @GetMapping("gif/save/form/{jour}/{mois}")
    public String gifDistant(
            Model model,
            @PathVariable(value = "jour") String jour,
            @PathVariable(value = "mois") String mois,
            @RequestParam(value = "distant", defaultValue = "true") String distant
    ) {
        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        DayCal jourEntity = jourService.findById(jourId);

        model.addAttribute("jour", jourEntity);
        model.addAttribute("distant", Boolean.parseBoolean(distant));

        return "gif";
    }

    @PostMapping("gif/save/form/{jour}/{mois}")
    public String addGif(
            String url,
            String legende,
            MultipartFile file,
            @PathVariable(value = "jour") String jour,
            @PathVariable(value = "mois") String mois
    ) {
        UserCal utilisateur = utilisateurService.utilisateurFromSecurityContext(SecurityContextHolder.getContext());

        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        DayCal jourEntity = jourService.findById(jourId);

        if (jourEntity.getGif() != null) {
            throw new IllegalArgumentException("Gif déjà ajouté");
        }

        if (utilisateur.getSolde() < jourEntity.getPoints()) {
            throw new IllegalArgumentException("Solde insuffisant");
        }

        if (file != null) {
            try {
                url = gifService.store(file);
            } catch (Exception e) {
                throw new IllegalArgumentException("Erreur lors de la sauvegarde du fichier : " + e.getMessage());
            }
        }

        // Création du gif
        Gif gif = new Gif();
        gif.setUrl(url);
        gif.setLegende(legende);
        gifService.save(gif);

        // Ajout du gif au jour
        jourService.setGif(jourId, gif, utilisateur);

        // Soustraction des points
        utilisateurService.subractPoints(utilisateur, jourEntity.getPoints());

        return "redirect:/";
    }

    @GetMapping("/jour/reaction/{jour}/{mois}/{reaction}")
    public String addReaction(
            @PathVariable(value = "jour") String jour,
            @PathVariable(value = "mois") String mois,
            @PathVariable(value = "reaction") String reaction
    ) {
        UserCal utilisateur = utilisateurService.utilisateurFromSecurityContext(SecurityContextHolder.getContext());

        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));

        ReactionType typeReactionEntity = typeReactionService.findById(Long.parseLong(reaction));
        reactionJourService.addOrRemoveReactionJour(jourId, typeReactionEntity, utilisateur);

        return "redirect:/";
    }
}

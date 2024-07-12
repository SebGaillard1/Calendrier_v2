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

    private final UserService userService;
    private final UserMapper userMapper;
    private final DayService dayService;
    private final GifService gifService;
    private final ReactionTypeService reactionTypeService;
    private final DayReactionService reactionDayService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid UserDto userDto) {
        userService.save(userMapper.toEntity(userDto));
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

        model.addAttribute("day", dayService.findAll(pageable));
        model.addAttribute("reactionType", reactionTypeService.findAll());

        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        StringBuilder sortBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            sortBuilder.append(order.getProperty());
            sortBuilder.append(',');
            sortBuilder.append(order.getDirection());
            if (iterator.hasNext()) {
                sortBuilder.append("&sort=");
            }
        }
        model.addAttribute("sort", sortBuilder.toString());

        return "index";
    }

    @GetMapping("gif/save/form/{day}/{month}")
    public String gifDistant(
            Model model,
            @PathVariable(value = "day") String jour,
            @PathVariable(value = "month") String mois,
            @RequestParam(value = "distant", defaultValue = "true") String distant
    ) {
        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        DayCal dayEntity = dayService.findById(jourId);

        model.addAttribute("jour", dayEntity);
        model.addAttribute("distant", Boolean.parseBoolean(distant));

        return "gif";
    }

    @PostMapping("gif/save/form/{day}/{month}")
    public String addGif(
            String url,
            String legende,
            MultipartFile file,
            @PathVariable(value = "day") String jour,
            @PathVariable(value = "month") String mois
    ) {
        UserCal user = userService.utilisateurFromSecurityContext(SecurityContextHolder.getContext());

        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));
        DayCal dayEntity = dayService.findById(jourId);

        if (dayEntity.getGif() != null) {
            throw new IllegalArgumentException("Gif already added");
        }

        if (user.getSolde() < dayEntity.getPoints()) {
            throw new IllegalArgumentException("Solde insuffisant");
        }

        if (file != null) {
            try {
                url = gifService.store(file);
            } catch (Exception e) {
                throw new IllegalArgumentException("Erreur lors de la sauvegarde du fichier : " + e.getMessage());
            }
        }

        Gif gif = new Gif();
        gif.setUrl(url);
        gif.setLegende(legende);
        gifService.save(gif);

        dayService.setGif(jourId, gif, user);

        userService.subractPoints(user, dayEntity.getPoints());

        return "redirect:/";
    }

    @GetMapping("/jour/reaction/{day}/{month}/{reaction}")
    public String addReaction(
            @PathVariable(value = "day") String jour,
            @PathVariable(value = "month") String mois,
            @PathVariable(value = "reaction") String reaction
    ) {
        UserCal user = userService.utilisateurFromSecurityContext(SecurityContextHolder.getContext());

        IdDay jourId = new IdDay(Integer.parseInt(jour), Integer.parseInt(mois));

        ReactionType typeReactionEntity = reactionTypeService.findById(Long.parseLong(reaction));
        reactionDayService.addOrRemoveReactionJour(jourId, typeReactionEntity, user);

        return "redirect:/";
    }
}

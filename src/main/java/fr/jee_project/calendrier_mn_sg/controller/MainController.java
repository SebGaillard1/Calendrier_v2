package fr.jee_project.calendrier_mn_sg.controller;

import fr.jee_project.calendrier_mn_sg.business.Gif;
import fr.jee_project.calendrier_mn_sg.business.DayCal;
import fr.jee_project.calendrier_mn_sg.business.ReactionType;
import fr.jee_project.calendrier_mn_sg.business.User;
import fr.jee_project.calendrier_mn_sg.business.Id.IdDay;
import fr.jee_project.calendrier_mn_sg.dto.UserDto;
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
    private final ReactionTypeService typeReactionService;
    private final DayReactionService reactiondayService;

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

        model.addAttribute("days", dayService.findAll(pageable));
        model.addAttribute("typeReactions", typeReactionService.findAll());

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

    @GetMapping("gif/save/form/{day}/{mois}")
    public String gifDistant(
            Model model,
            @PathVariable(value = "day") String day,
            @PathVariable(value = "mois") String mois,
            @RequestParam(value = "distant", defaultValue = "true") String distant
    ) {
        IdDay dayId = new IdDay(Integer.parseInt(day), Integer.parseInt(mois));
        DayCal dayEntity = dayService.findById(dayId);

        model.addAttribute("day", dayEntity);
        model.addAttribute("distant", Boolean.parseBoolean(distant));

        return "gif";
    }

    @PostMapping("gif/save/form/{day}/{mois}")
    public String addGif(
            String url,
            String legende,
            MultipartFile file,
            @PathVariable(value = "day") String day,
            @PathVariable(value = "mois") String mois
    ) {
        User user = userService.userFromSecurityContext(SecurityContextHolder.getContext());

        IdDay dayId = new IdDay(Integer.parseInt(day), Integer.parseInt(mois));
        DayCal dayEntity = dayService.findById(dayId);

        if (dayEntity.getGif() != null) {
            throw new IllegalArgumentException("Gif déjà ajouté");
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

        dayService.setGif(dayId, gif, user);

        userService.subractPoints(user, dayEntity.getPoints());

        return "redirect:/";
    }

    @GetMapping("/day/reaction/{day}/{mois}/{reaction}")
    public String addReaction(
            @PathVariable(value = "day") String day,
            @PathVariable(value = "mois") String mois,
            @PathVariable(value = "reaction") String reaction
    ) {
        User user = userService.userFromSecurityContext(SecurityContextHolder.getContext());

        IdDay dayId = new IdDay(Integer.parseInt(day), Integer.parseInt(mois));

        ReactionType typeReactionEntity = typeReactionService.findById(Long.parseLong(reaction));
        reactiondayService.addOrRemoveReactionDay(dayId, typeReactionEntity, user);

        return "redirect:/";
    }
}

package de.bigzee.siegertipp.player;

import com.google.common.io.ByteStreams;
import de.bigzee.siegertipp.account.Account;
import de.bigzee.siegertipp.account.UserService;
import de.bigzee.siegertipp.model.Gender;
import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.model.Player;
import de.bigzee.siegertipp.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/player")
class PlayerController {

    private static final String HOME = "player/list";
    private static final String SHOW = "player/show";
    private static final String CREATE = "player/create";

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String show(@PathVariable long id, Model model) {
        model.addAttribute(playerRepository.findById(id));
        return SHOW;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute(new Player("", null));
        return CREATE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Player player, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE;
        }
        player.setCreatedBy(userService.current());
        playerRepository.save(player);
        MessageHelper.addSuccessAttribute(ra, "player.create.success", player.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute Player player, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SHOW;
        }
        playerRepository.update(player);
        MessageHelper.addSuccessAttribute(ra, "player.update.success", player.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return HOME;
    }

    @RequestMapping(value = "/{id}/picture", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public byte[] picture(@PathVariable Long id) {
        Assert.notNull(id);
        Player player = playerRepository.findById(id);

        if (player.getPicture() != null) {
            return player.getPicture();
        }
        //TODO fix
        byte[] emptyPicture =
                new byte[0];
        try {
            emptyPicture = ByteStreams.toByteArray(this.getClass().getClassLoader().getResourceAsStream("empty_avatar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emptyPicture;
    }

    @ModelAttribute("players")
    public List<Player> populatePlayers() {
        return playerRepository.findAll();
    }

    @ModelAttribute("genders")
    public List<Gender> populateGenders() {
        return Arrays.asList(Gender.values());
    }

}

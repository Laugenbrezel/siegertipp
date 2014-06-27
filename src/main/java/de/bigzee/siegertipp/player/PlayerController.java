package de.bigzee.siegertipp.player;

import de.bigzee.siegertipp.account.UserService;
import de.bigzee.siegertipp.model.Gender;
import de.bigzee.siegertipp.model.Player;
import de.bigzee.siegertipp.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
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

    private UserService userService;

    @Autowired
    public PlayerController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable String id, Model model) {
        model.addAttribute(playerRepository.findOne(id));
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
        player.setCreatedBy(userService.current().getEmail());
        playerRepository.save(player);
        MessageHelper.addSuccessAttribute(ra, "player.create.success", player.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute Player player, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SHOW;
        }
        playerRepository.save(player);
        MessageHelper.addSuccessAttribute(ra, "player.update.success", player.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/update/{id}/picture", method = RequestMethod.POST)
    public String updatePicture(@RequestPart("playerPicture") Part playerPicture, @PathVariable String id, Errors errors, RedirectAttributes ra) throws IOException {
        if (errors.hasErrors()) {
            return SHOW;
        }
        Player player = playerRepository.findOne(id);
        if (playerPicture.getSize() > 0) {
            player.setPicture(StreamUtils.copyToByteArray(playerPicture.getInputStream()));
        } else {
            player.setPicture(null);
        }
        playerRepository.save(player);

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
    public byte[] picture(@PathVariable String id) {
        Assert.notNull(id);
        Player player = playerRepository.findOne(id);

        if (player.getPicture() != null) {
            return player.getPicture();
        }
        //TODO fix
        byte[] emptyPicture =
                new byte[0];
        try {
            emptyPicture = StreamUtils.copyToByteArray(this.getClass().getClassLoader().getResourceAsStream("empty_avatar.png"));
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

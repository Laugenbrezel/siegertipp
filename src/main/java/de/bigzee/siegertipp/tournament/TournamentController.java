package de.bigzee.siegertipp.tournament;

import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.model.Tournament;
import de.bigzee.siegertipp.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/tournament")
class TournamentController {

    private static final String HOME = "tournament/list";
    private static final String CREATE = "tournament/create";
    private static final String CREATE_GROUP = "group/create";

    @Autowired
    private TournamentRepository tournamentRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute(new Tournament(""));
        return CREATE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Tournament tournament, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE;
        }
        tournamentRepository.save(tournament);
        MessageHelper.addSuccessAttribute(ra, "tournament.create.success", tournament.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return HOME;
    }

    @ModelAttribute("tournaments")
    public List<Tournament> populateTournaments() {
        return tournamentRepository.findAll();
    }

    @RequestMapping(value = "/{id}/group/add", method = RequestMethod.GET)
    public String newGroup(@PathVariable String id, Model model) {
        Tournament tournament = tournamentRepository.findOne(id);
        model.addAttribute(new Group("", tournament));
        return CREATE_GROUP;
    }

    @RequestMapping(value = "/{id}/group/add", method = RequestMethod.POST)
    public String addGroup(@PathVariable String id, @Valid @ModelAttribute Group group, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE_GROUP;
        }
        //FIXME make setting the id stop
        group.setId(null);
        Tournament tournament = tournamentRepository.findOne(id);
        tournament.addGroup(group);
        tournamentRepository.save(tournament);
        MessageHelper.addSuccessAttribute(ra, "group.create.success", group.getName());
        return "redirect:/" + HOME;
    }
}

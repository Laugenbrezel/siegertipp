package de.bigzee.siegertipp.tournament;

import de.bigzee.siegertipp.group.GroupRepository;
import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.model.Tournament;
import de.bigzee.siegertipp.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
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

    @Autowired
    private GroupRepository groupRepository;

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
    public String newGroup(@PathVariable Long id, Model model) {
        Tournament tournament = tournamentRepository.findById(id);
        model.addAttribute(new Group("", tournament));
        return CREATE_GROUP;
    }

    @RequestMapping(value = "/{id}/group/add", method = RequestMethod.POST)
    public String addGroup(@PathVariable Long id, @Valid @ModelAttribute Group group, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE_GROUP;
        }
        //FIXME make setting the id stop
        group.setId(null);
        tournamentRepository.addGroup(id, group);
        MessageHelper.addSuccessAttribute(ra, "group.create.success", group.getName());
        return "redirect:/" + HOME;
    }
}

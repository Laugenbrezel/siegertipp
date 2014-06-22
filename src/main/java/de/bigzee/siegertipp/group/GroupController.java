package de.bigzee.siegertipp.group;

import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.model.Tournament;
import de.bigzee.siegertipp.support.web.MessageHelper;
import de.bigzee.siegertipp.tournament.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/group")
class GroupController {

    private static final String HOME = "group/list";
    private static final String CREATE = "group/create";

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute(new Group("", null));
        return CREATE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Group group, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE;
        }
        groupRepository.save(group);
        MessageHelper.addSuccessAttribute(ra, "group.create.success", group.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return HOME;
    }

    @ModelAttribute("groups")
    public List<Group> populateGroups() {
        return groupRepository.findAll();
    }

    @ModelAttribute("tournaments")
    public List<Tournament> populateTournaments() {
        return tournamentRepository.findAll();
    }

}

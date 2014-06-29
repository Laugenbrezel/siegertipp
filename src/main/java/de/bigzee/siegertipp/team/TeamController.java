package de.bigzee.siegertipp.team;

import de.bigzee.siegertipp.group.GroupRepository;
import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.model.Team;
import de.bigzee.siegertipp.support.web.MessageHelper;
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
import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/team")
class TeamController {

    private static final String HOME = "team/list";
    private static final String CREATE = "team/create";

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute(new Team(""));
        return CREATE;
    }

    //TODO implement service layer
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Team team, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE;
        }
        teamRepository.save(team);
        MessageHelper.addSuccessAttribute(ra, "team.create.success", team.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return HOME;
    }

    @ModelAttribute("teams")
    public List<Team> populateTeams() {
        return teamRepository.findAll();
    }

    @ModelAttribute("groups")
    public List<Group> populateGroups() {
        return groupRepository.findAll();
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Group.class, "group", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Group group = groupRepository.findOne(text);
                setValue(group);
            }
        });
    }
}

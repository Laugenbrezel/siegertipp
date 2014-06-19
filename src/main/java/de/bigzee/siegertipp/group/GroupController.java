package de.bigzee.siegertipp.group;

import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_USER")
class GroupController {

    private static final String HOME = "group/list";
    private static final String CREATE = "group/create";

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(value = "/group/create")
    public String create(Model model) {
        model.addAttribute(new GroupForm());
        return CREATE;
    }

    @RequestMapping(value = "/group/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute GroupForm groupForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return CREATE;
        }
        Group group = groupRepository.save(groupForm.createGroup());
        MessageHelper.addSuccessAttribute(ra, "group.create.success", group.getName());
        return "redirect:/" + HOME;
    }

    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public String list() {
        return HOME;
    }

    @ModelAttribute("groups")
    public List<Group> groups() {
        return groupRepository.findAll();
    }
}

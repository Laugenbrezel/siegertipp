package de.bigzee.siegertipp.group;

import de.bigzee.siegertipp.account.Account;
import de.bigzee.siegertipp.model.Group;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class GroupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = GroupForm.NOT_BLANK_MESSAGE)
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group createGroup() {
        return new Group(getName());
	}
}

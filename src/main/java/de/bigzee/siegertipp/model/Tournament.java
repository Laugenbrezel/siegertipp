package de.bigzee.siegertipp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lzimmerm on 19.06.2014.
 */
@SuppressWarnings("serial")
@Document(collection = "tournaments")
public class Tournament implements Serializable {

    @Id
    private String id;

    @NotNull
    private String name;

    private Date startDate;

    private Date endDate;

    @DBRef
    private List<Group> groups = new ArrayList<>();

    protected Tournament() {
    }

    public Tournament(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void addGroup(Group group) {
        group.setTournament(this);
        getGroups().add(group);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tournament)) return false;

        Tournament group = (Tournament) o;

        if (!name.equals(group.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

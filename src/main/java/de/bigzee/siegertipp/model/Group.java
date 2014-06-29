package de.bigzee.siegertipp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzimmerm on 19.06.2014.
 */
@SuppressWarnings("serial")
@Document(collection = "groups")
//@Entity
//@Table(name = "match_group",  uniqueConstraints={
//        @UniqueConstraint(columnNames={"name", "tournament_id"})
//})
public class Group implements Serializable {
    @Id
    private String id;

    @NotNull
    private String name;

    @DBRef
    private Tournament tournament;

    @DBRef
    private List<Team> teams = new ArrayList<>();

    protected Group() {
    }

    public Group(String name, Tournament tournament) {
        this.name = name;
        this.tournament = tournament;
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

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}

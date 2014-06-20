package de.bigzee.siegertipp.team;

import de.bigzee.siegertipp.model.Team;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TeamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Team save(Team team) {
        entityManager.persist(team);
        return team;
    }

    @Transactional
    public Team update(Team team) {
        entityManager.merge(team);
        return team;
    }

    public List<Team> findAll() {
        return entityManager.createQuery("select t from Team t order by t.name", Team.class).getResultList();
    }
}

package de.bigzee.siegertipp.tournament;

import de.bigzee.siegertipp.model.Group;
import de.bigzee.siegertipp.model.Tournament;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TournamentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Tournament save(Tournament tournament) {
        entityManager.persist(tournament);
        return tournament;
    }

    @Transactional
    public Tournament update(Tournament tournament) {
        entityManager.merge(tournament);
        return tournament;
    }

    public List<Tournament> findAll() {
        return entityManager.createQuery("select distinct(t) from Tournament t left join fetch t.groups g order by t.startDate", Tournament.class)
                .getResultList();
    }

    public Tournament findById(Long id) {
        return entityManager.find(Tournament.class, id);
    }

    public void addGroup(Long id, Group group) {
        Tournament tournament = findById(id);
        tournament.addGroup(group);
        entityManager.merge(tournament);
    }
}

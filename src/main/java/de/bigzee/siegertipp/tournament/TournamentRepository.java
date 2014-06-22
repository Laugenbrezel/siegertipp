package de.bigzee.siegertipp.tournament;

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
        return entityManager.createQuery("select t from Tournament t left join fetch t.groups order by t.startDate", Tournament.class)
                .getResultList();
    }

    public Tournament findById(Long id) {
        return entityManager.find(Tournament.class, id);
    }
}

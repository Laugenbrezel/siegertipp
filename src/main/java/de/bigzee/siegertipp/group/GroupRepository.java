package de.bigzee.siegertipp.group;

import de.bigzee.siegertipp.model.Group;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class GroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Group save(Group group) {
        entityManager.persist(group);
        return group;
    }

    @Transactional
    public Group update(Group group) {
        entityManager.merge(group);
        return group;
    }

    public List<Group> findAll() {
        return entityManager.createQuery("select p from Group p left join fetch p.teams order by p.name", Group.class).getResultList();
    }

    public Group findById(Long id) {
        return entityManager.find(Group.class, id);
    }
}

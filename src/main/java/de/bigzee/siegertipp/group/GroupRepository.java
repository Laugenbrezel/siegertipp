package de.bigzee.siegertipp.group;

import de.bigzee.siegertipp.model.Group;
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
    public Group save(Group Group) {
        entityManager.persist(Group);
        return Group;
    }

    @Transactional
    public Group update(Group Group) {
        entityManager.merge(Group);
        return Group;
    }

    public List<Group> findAll() {
        return entityManager.createQuery("select p from Group p order by p.name", Group.class).getResultList();
    }

    public Group findById(Long id) {
        return entityManager.find(Group.class, id);
    }
}

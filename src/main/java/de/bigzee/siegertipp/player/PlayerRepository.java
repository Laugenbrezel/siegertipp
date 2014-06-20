package de.bigzee.siegertipp.player;

import de.bigzee.siegertipp.account.Account;
import de.bigzee.siegertipp.model.Player;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PlayerRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Player save(Player player) {
		entityManager.persist(player);
		return player;
	}
	
	public Player findByName(String name) {
		try {
			return entityManager.createNamedQuery(Player.FIND_BY_NAME, Player.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}


    public Player findById(Long id) {
        return entityManager.find(Player.class, id);
    }

    public List<Player> findAll() {
        return entityManager.createQuery("select p from Player p order by p.name", Player.class).getResultList();
    }

    @Transactional
    public Player update(Player player) {
        return entityManager.merge(player);
    }
}

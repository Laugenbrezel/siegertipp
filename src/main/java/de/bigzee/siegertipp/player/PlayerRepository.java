package de.bigzee.siegertipp.player;

import de.bigzee.siegertipp.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findByName(@Param("name") String name);
}

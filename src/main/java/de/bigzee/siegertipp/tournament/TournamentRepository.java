package de.bigzee.siegertipp.tournament;

import de.bigzee.siegertipp.model.Tournament;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface TournamentRepository extends MongoRepository<Tournament, String> {

}

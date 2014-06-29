package de.bigzee.siegertipp.team;

import de.bigzee.siegertipp.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teams", path = "teams")
public interface TeamRepository extends MongoRepository<Team, String> {

}

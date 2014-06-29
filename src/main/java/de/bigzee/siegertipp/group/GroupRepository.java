package de.bigzee.siegertipp.group;

import de.bigzee.siegertipp.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "groups", path = "groups")
public interface GroupRepository extends MongoRepository<Group, String> {

}

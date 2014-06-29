package de.bigzee.siegertipp.account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends MongoRepository<Account, String> {

    @Query("{'email':?0}")
    Account findByEmail(@Param("email") String email);
}
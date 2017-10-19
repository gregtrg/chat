package org.mycompany.chat.repository;

import org.mycompany.chat.domain.JwtToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<JwtToken, String> {

//    @EntityGraph(attributePaths = {"user.id"})
    @Cacheable(cacheNames = "tokens")
    JwtToken findOneByToken(String token);
}

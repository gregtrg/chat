package org.mycompany.chat.security;

import org.mycompany.chat.domain.UserSecurityDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityDetailsRepository extends CrudRepository<UserSecurityDetails, Long> {
    Optional<UserSecurityDetails> findOneByEmail(String email);
}

package org.mycompany.chat.repository;

import org.mycompany.chat.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> getAuthorByUsername(String username);
}

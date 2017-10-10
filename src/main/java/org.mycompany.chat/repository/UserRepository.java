package org.mycompany.chat.repository;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    public Optional<User> getUserDetailsByUsername(String username) {
        return null;
    }
}

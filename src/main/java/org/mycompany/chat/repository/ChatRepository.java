package org.mycompany.chat.repository;

import org.mycompany.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Message, Long> { }

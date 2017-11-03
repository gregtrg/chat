package org.mycompany.chat.repository;

import org.mycompany.chat.domain.Chat;
import org.mycompany.chat.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Override
    @EntityGraph(attributePaths = {
        "members.id",
        "members.firstName",
        "members.lastName",
        "messages.id",
        "messages.message",
        "messages.messageTime",
        "messages.user.id",
        "messages.user.firstName",
        "messages.user.lastName"})
    Chat getOne(Long aLong);

    Page<Chat> findAllByMembers(Pageable page, User user);

}

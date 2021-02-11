package by.kashlyak.integrator.integratorby.telegrambot.repository;


import by.kashlyak.integrator.integratorby.telegrambot.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
    Messages findMessageByMessageType(String messageType);
}

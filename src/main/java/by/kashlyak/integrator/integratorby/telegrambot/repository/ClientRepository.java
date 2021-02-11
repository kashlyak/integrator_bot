package by.kashlyak.integrator.integratorby.telegrambot.repository;

import by.kashlyak.integrator.integratorby.telegrambot.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByChatId(Long chatId);

}

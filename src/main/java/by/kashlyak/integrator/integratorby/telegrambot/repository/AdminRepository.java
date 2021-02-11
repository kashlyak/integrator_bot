package by.kashlyak.integrator.integratorby.telegrambot.repository;

import by.kashlyak.integrator.integratorby.telegrambot.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByChatId(Long chatId);
}

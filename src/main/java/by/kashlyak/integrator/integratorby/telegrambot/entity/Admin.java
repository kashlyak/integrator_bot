package by.kashlyak.integrator.integratorby.telegrambot.entity;

import by.kashlyak.integrator.integratorby.telegrambot.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name= "name")
    String name;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "bot_state")
    BotState botState;
}

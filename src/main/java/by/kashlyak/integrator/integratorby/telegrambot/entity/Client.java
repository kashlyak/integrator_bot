package by.kashlyak.integrator.integratorby.telegrambot.entity;

import by.kashlyak.integrator.integratorby.telegrambot.enums.BotState;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="clients_table")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name="name")
    private String name;
    @Column(name = "bot_state")
    BotState botState;
    @Column(name = "telegram")
    boolean telegram=false;
    @Column(name = "viber")
    boolean viber=false;
    @Column(name = "whatsApp")
    boolean whatsApp=false;
    @Column(name = "instagram")
    boolean instagram=false;
    @Column(name = "vk")
    boolean vk=false;
    @Column(name = "integration")
    private String integration;
    @Column( name = "message", columnDefinition ="text")
    private String message;
    @Column(name= "personal_info")
    private String personalInfo;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", chatId=" + chatId +

                ", telegram=" + telegram +
                ", viber=" + viber +
                ", whatsApp=" + whatsApp +
                ", instagram=" + instagram +
                ", vk=" + vk +
                ", integration='" + integration + '\'' +
                ", message='" + message + '\'' +
                ", personalInfo='" + personalInfo + '\'' +
                '}';
    }
}

package by.kashlyak.integrator.integratorby.telegrambot.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "type")
    private String messageType;
    @Column(name = "message", columnDefinition = "text")
    private String messegeForCleint;

}

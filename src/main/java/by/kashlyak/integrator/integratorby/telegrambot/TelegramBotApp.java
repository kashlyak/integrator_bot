package by.kashlyak.integrator.integratorby.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication

public class TelegramBotApp {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        SpringApplication.run(TelegramBotApp.class, args);
    }


}

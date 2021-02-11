package by.kashlyak.integrator.integratorby.telegrambot.service;

import by.kashlyak.integrator.integratorby.telegrambot.IntegratorBot;
import by.kashlyak.integrator.integratorby.telegrambot.buttons.AdminButtons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AdminMessageService {
    @Autowired
    AdminButtons adminButtons;
    @Autowired
    IntegratorBot integratorBot;

    public void sendMessage(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode("HTML");
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(adminButtons.getAdminMainMenu());
        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageForWorkWithClients(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode("HTML");
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(adminButtons.getAdminClientMenu());
        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageForWorkWithAdmins(Update update, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode("HTML");
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(adminButtons.getAdminMenuForAdminDB());
        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageForWorkWithMessages(Update update, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode("HTML");
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(adminButtons.getAdminButtonsForMessageDB());
        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageForClients(Long id, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setParseMode("HTML");
        sendMessage.setText(text);

        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}

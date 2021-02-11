package by.kashlyak.integrator.integratorby.telegrambot.service;

import by.kashlyak.integrator.integratorby.telegrambot.IntegratorBot;
import by.kashlyak.integrator.integratorby.telegrambot.buttons.ClientsButtons;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Client;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Messages;
import by.kashlyak.integrator.integratorby.telegrambot.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageService {


    @Autowired
    ClientsButtons clientsButtons;
    @Autowired
    IntegratorBot integratorBot;

    @Autowired
    MessagesRepository messagesRepository;

    public void sendMessage(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode("HTML");
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(clientsButtons.getMainMenuKeyboard());
        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithCallBackQueryForPlatform(Update update, String text) {
        System.out.println(update.getMessage().getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        System.out.println(update.getMessage().getChatId());
        sendMessage.setText(text);

        try {
            sendMessage.setReplyMarkup(clientsButtons.getInlineMessageButtonsForPlatformAfterChoose(update.getMessage().getChatId()));
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithCallBackQueryForIntegration(Update update, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());

        sendMessage.setText(text);

        sendMessage.setReplyMarkup(clientsButtons.getInlineMessageButtonsForIntegration());


        try {

            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithoutCallBackQueryForMessage(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText(text);
        try {
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void editMessage(Update update, Client client) {
        Messages platform = messagesRepository.findMessageByMessageType("platform");
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setReplyMarkup(clientsButtons.getInlineMessageButtonsForPlatformAfterChoose(client));
        editMessageText.setText(platform.getMessegeForCleint());
        editMessageText.enableMarkdown(true);

        try {
            integratorBot.execute(editMessageText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithoutCallBackQueryForMessageForContact(Update update, String text) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());

        sendMessage.setText(text);
        try {
            sendMessage.setReplyMarkup(clientsButtons.sharedPhoneNumber());
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithoutCallBackQueryForSharedManagerContact(Update update, String text) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());

        sendMessage.setText(text);
        try {
            sendMessage.setReplyMarkup(clientsButtons.getManagerInlineApproveMarkup());
            integratorBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageForAdmins(Long id, String text) {
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



package by.kashlyak.integrator.integratorby.telegrambot.service;


import by.kashlyak.integrator.integratorby.telegrambot.IntegratorBot;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Admin;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Client;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Messages;
import by.kashlyak.integrator.integratorby.telegrambot.enums.BotState;
import by.kashlyak.integrator.integratorby.telegrambot.processor.*;
import by.kashlyak.integrator.integratorby.telegrambot.repository.AdminRepository;
import by.kashlyak.integrator.integratorby.telegrambot.repository.ClientRepository;
import by.kashlyak.integrator.integratorby.telegrambot.repository.MessagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@Slf4j
public class RequestDispatcher {
    @Autowired
    IntegratorBot integratorBot;
    @Autowired
    Client client;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    HelloProcessor helloProcessor;
    @Autowired
    HelloAdminProcessor platformProcessor;
    @Autowired
    MessagesRepository messagesRepository;
    @Autowired
    ScopeProcessor scopeProcessor;
    @Autowired
    AboutProcessor aboutProcessor;
    @Autowired
    ManagerProcessor managerProcessor;
    @Autowired
    CIProcessor ciProcessor;
    @Autowired
    AdminMessageHandler adminMessageHandler;

    public void dispatch(Update update) {
        Long id = setChatId(update);
        Admin adminByChatId = adminRepository.findAdminByChatId(id);
        if (adminByChatId != null) {
            adminMessageHandler.saveAdminInfo(update, adminByChatId);
            adminMessageHandler.handleInputMessageForAdmin(update);
        } else if (clientRepository.findClientByChatId(id) == null) {
            Client client = new Client();
            client.setName(update.getMessage().getChat().getFirstName());
            client.setChatId(id);
            client.setBotState(BotState.HELLO);
            clientRepository.save(client);

            handleInputMessage(update, client);
        } else {
            Client client = clientRepository.findClientByChatId(id);
            handleInputMessage(update, client);
        }
    }


    private void handleInputMessage(Update update, Client client) {
        if (update.hasCallbackQuery()) {
            processCallBackQueryForClient(client, update);
        } else if (update.getMessage().hasContact()) {
            savePersonalInfo(update, client);
            workWithState(update, client);
        } else if (update.getMessage().getText().equals("/start")) {
            client.setBotState(BotState.HELLO);
            workWithState(update, client);
        } else {
            workWithState(update, client);
        }

    }

    public void workWithState(Update update, Client client) {
        switch (client.getBotState()) {
            case HELLO:
                workWithClientWithoutBotState(update, client);
                break;

            case ASK_PERSONALINFO:
                workWithClient(update, client);
                break;
            case SHOW_MAIN_MENU:
                workWithClient(update, client);

                break;
        }
    }


    private void workWithClientWithoutBotState(Update update, Client client) {
        String message = update.getMessage().getText();
        if (message.toLowerCase().contains("/start")) {
            client.setBotState(BotState.HELLO);
            clientRepository.save(client);
            messageService.sendMessage(update, helloProcessor.run());
        } else if (message.toLowerCase().contains("умеют")) {
            messageService.sendMessage(update, aboutProcessor.run());
        } else if (message.toLowerCase().contains("brief")) {
            Messages platform = messagesRepository.findMessageByMessageType("platform");
            messageService.sendMessageWithCallBackQueryForPlatform(update, platform.getMessegeForCleint());
        } else if (message.toLowerCase().contains("сферы")) {

            messageService.sendMessage(update, scopeProcessor.run());
        } else if (message.toLowerCase().contains("стоимость")) {
            messageService.sendMessage(update, ciProcessor.run());
        } else if (message.toLowerCase().contains("менеджер")) {
            messageService.sendMessageWithoutCallBackQueryForSharedManagerContact(update, managerProcessor.run());
        }
    }


    private void workWithClient(Update update, Client client) {
        BotState botState = client.getBotState();
        switch (botState) {
            case HELLO:
                messageService.sendMessage(update, helloProcessor.run());
                break;
            case ASK_INTEGRATION:
                Messages integration = messagesRepository.findMessageByMessageType("integration");

                messageService.sendMessageWithCallBackQueryForIntegration(update, integration.getMessegeForCleint());
                break;
            case ASK_MESSAGE:
                Messages messages = messagesRepository.findMessageByMessageType("message");
                messageService.sendMessageWithoutCallBackQueryForMessage(update, messages.getMessegeForCleint());
                client.setBotState(BotState.ASK_PERSONALINFO);
                clientRepository.save(client);
                break;
            case ASK_PERSONALINFO:
                Messages personalInfo = messagesRepository.findMessageByMessageType("personalinfo");
                client.setBotState(BotState.SHOW_MAIN_MENU);
                saveMessage(update, client);
                messageService.sendMessageWithoutCallBackQueryForMessageForContact(update, personalInfo.getMessegeForCleint());
                break;
            case SHOW_MAIN_MENU:
                Messages end = messagesRepository.findMessageByMessageType("end");
                savePersonalInfo(update, client);
                if (update.getMessage().getText().toLowerCase().equals("нет")) {
                    Messages no = messagesRepository.findMessageByMessageType("brief_answer_no");
                    messageService.sendMessage(update, no.getMessegeForCleint());
                } else {
                    messageService.sendMessage(update, end.getMessegeForCleint());
                }
                client.setBotState(BotState.HELLO);
                clientRepository.save(client);
                sendMessageForAdmins(client);
                break;
        }
    }

    private void sendMessageForAdmins(Client client) {
        List<Admin> all = adminRepository.findAll();
        for (Admin admin : all) {
            Long chatId = admin.getChatId();
            messageService.sendMessageForAdmins(chatId, adminMessageHandler.getMessageAboutClient(client));
        }
    }

    private void saveMessage(Update update, Client client) {
        if (update.hasMessage()) {
            client.setMessage(update.getMessage().getText());
            clientRepository.save(client);
        }
    }

    private void savePersonalInfo(Update update, Client client) {
        if (update.getMessage().hasContact()) {
            client.setPersonalInfo(update.getMessage().getContact().getPhoneNumber());
            client.setName(update.getMessage().getContact().getFirstName());
        } else {
            client.setPersonalInfo(update.getMessage().getText());
        }
        client.setBotState(BotState.SHOW_MAIN_MENU);
        clientRepository.save(client);
    }


    private void processCallBackQueryForClient(Client client, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        if (callbackQuery.getData().equals("Instagram")) {
            if (!client.isInstagram()) {
                client.setInstagram(true);
            } else {
                client.setInstagram(false);
            }


            messageService.editMessage(update, client);
            client.setBotState(BotState.ASK_PLATFORM);


        } else if (callbackQuery.getData().equals("Telegram")) {
            if (!client.isTelegram()) {
                client.setTelegram(true);
            } else {
                client.setTelegram(false);
            }


            messageService.editMessage(update, client);
            client.setBotState(BotState.ASK_PLATFORM);


        } else if (callbackQuery.getData().equals("Viber")) {
            if (!client.isViber()) {
                client.setViber(true);
            } else {
                client.setViber(false);
            }


            messageService.editMessage(update, client);
            client.setBotState(BotState.ASK_PLATFORM);


        } else if (callbackQuery.getData().equals("Vk")) {
            if (!client.isVk()) {
                client.setVk(true);

            } else {
                client.setVk(false);

            }


            messageService.editMessage(update, client);
            client.setBotState(BotState.ASK_PLATFORM);


        } else if (callbackQuery.getData().equals("WhatsApp")) {
            if (!client.isWhatsApp()) {
                client.setWhatsApp(true);
            } else {
                client.setWhatsApp(false);
            }
//            clientRepository.save(client);

            messageService.editMessage(update, client);
            client.setBotState(BotState.ASK_PLATFORM);


        } else if (callbackQuery.getData().equals("Next")) {

            client.setBotState(BotState.ASK_INTEGRATION);
            clientRepository.save(client);

            workWithClient(update, client);

        } else if (callbackQuery.getData().equals("Need")) {
            client.setIntegration(update.getCallbackQuery().getData());
            client.setBotState(BotState.ASK_MESSAGE);
            clientRepository.save(client);
            workWithClient(update, client);


        } else if (callbackQuery.getData().equals("NoNeed")) {
            client.setIntegration(update.getCallbackQuery().getData());
            client.setBotState(BotState.ASK_MESSAGE);
            clientRepository.save(client);
            workWithClient(update, client);


        } else if (callbackQuery.getData().equals("1C")) {
            client.setIntegration(update.getCallbackQuery().getData());
            client.setBotState(BotState.ASK_MESSAGE);
            clientRepository.save(client);
            workWithClient(update, client);


        } else if (callbackQuery.getData().equals("warehouse")) {
            client.setIntegration(update.getCallbackQuery().getData());
            client.setBotState(BotState.ASK_MESSAGE);
            clientRepository.save(client);
            workWithClient(update, client);


        } else if (callbackQuery.getData().equals("CRM")) {
            client.setIntegration(update.getCallbackQuery().getData());
            client.setBotState(BotState.ASK_MESSAGE);
            clientRepository.save(client);
            workWithClient(update, client);


        } else if (callbackQuery.getData().equals("Another")) {
            client.setIntegration(update.getCallbackQuery().getData());
            client.setBotState(BotState.ASK_MESSAGE);
            clientRepository.save(client);
            workWithClient(update, client);
        }

        clientRepository.save(client);

    }

    private Long setChatId(Update update) {
        Long id;
        if (update.hasCallbackQuery()) {
            id = update.getCallbackQuery().getMessage().getChatId();
        } else {
            id = update.getMessage().getChatId();
        }
        return id;
    }


}



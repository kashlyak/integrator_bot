package by.kashlyak.integrator.integratorby.telegrambot.service;

import by.kashlyak.integrator.integratorby.telegrambot.entity.Admin;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Client;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Messages;
import by.kashlyak.integrator.integratorby.telegrambot.enums.BotState;
import by.kashlyak.integrator.integratorby.telegrambot.processor.HelloAdminProcessor;
import by.kashlyak.integrator.integratorby.telegrambot.repository.AdminRepository;
import by.kashlyak.integrator.integratorby.telegrambot.repository.ClientRepository;
import by.kashlyak.integrator.integratorby.telegrambot.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import static by.kashlyak.integrator.integratorby.telegrambot.enums.BotState.*;

@Component
public class AdminMessageHandler {
    @Autowired
    AdminMessageService adminMessageService;
    @Autowired
    HelloAdminProcessor helloAdminProcessor;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    MessagesRepository messagesRepository;


    public void handleInputMessageForAdmin(Update update) {
        String text = update.getMessage().getText();
        if (text.equals("/start")) {
            adminMessageService.sendMessage(update, helloAdminProcessor.run());
        } else {
            workWithAdmin(update);
        }
    }

    private Long id;
    private String type;

    private void workWithAdmin(Update update) {
        String text = update.getMessage().getText();

        Admin adminByChatId = adminRepository.findAdminByChatId(update.getMessage().getChatId());
        if (text.toLowerCase().contains("базу")) {
            adminMessageService.sendMessageForWorkWithClients(update, "Выберите пункт, пожалуйста.");
        } else if (text.toLowerCase().contains("администраторы")) {
            adminMessageService.sendMessageForWorkWithAdmins(update, "Выберите пункт, пожалуйста.");
//            workWithAdminBD(update);
        } else if (text.toLowerCase().contains("отредактировать")) {
            adminMessageService.sendMessageForWorkWithMessages(update, "Выберите пункт, пожалуйста.");

        } else if (text.toLowerCase().equals("отправить сообщение всем клиентам")) {
            adminByChatId.setBotState(ADMIN_SEND_MESSAGE);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Введите сообщение, которое нужно отправить");
        } else if (adminByChatId.getBotState() == ADMIN_SEND_MESSAGE) {
            String message = update.getMessage().getText();
            adminByChatId.setBotState(ADMIN);
            adminRepository.save(adminByChatId);
            Long aLong = sendMessageForClients(message);
            adminMessageService.sendMessage(update, "Отправлено " + aLong + " сообщений");
        } else if (text.toLowerCase().contains("все записи")) {
            List<Client> allClientsFromBd = findAllClientsFromBd();
            adminMessageService.sendMessageForWorkWithClients(update, getMessageAboutClients(allClientsFromBd));
        } else if (text.toLowerCase().contains("последнего")) {
            Client lastClientFromBd = findLastClientFromBd();
            adminMessageService.sendMessageForWorkWithClients(update, getMessageAboutClient(lastClientFromBd));
        } else if (text.toLowerCase().contains("посмотреть последние 5")) {
            List<Client> all = clientRepository.findAll();
            if (all.size() < 4) {
                adminMessageService.sendMessageForWorkWithClients(update, getMessageAboutClients(all));
            } else {
                List<Client> findLastFiveClientsFromBd = findLastFiveClientsFromBd();
                adminMessageService.sendMessageForWorkWithClients(update, getMessageAboutClients(findLastFiveClientsFromBd));
            }
        } else if (text.toLowerCase().contains("посмотреть последние 10")) {
            List<Client> all = clientRepository.findAll();
            if (all.size() < 9) {
                adminMessageService.sendMessageForWorkWithClients(update, getMessageAboutClients(all));
            } else {
                List<Client> findLastTenClientsFromBd = findLastTenClientsFromBd();
                adminMessageService.sendMessageForWorkWithClients(update, getMessageAboutClients(findLastTenClientsFromBd));
            }
        } else if (text.toLowerCase().contains("посмотреть всех")) {
            List<Admin> all = adminRepository.findAll();
            adminMessageService.sendMessageForWorkWithAdmins(update, getMessageAboutAdmins(all));
        } else if (text.toLowerCase().contains("добавить администратора")) {
            adminMessageService.sendMessageForWorkWithAdmins(update, "Введите chatId нового администратора.");

            adminByChatId.setBotState(BotState.ADMIN_ADD);
            adminRepository.save(adminByChatId);

        } else if (text.toLowerCase().contains("удалить администратора")) {
            adminMessageService.sendMessageForWorkWithAdmins(update, "Введите id администратора. Его можно посмотреть, если нажать на левую кнопку.");

            adminByChatId.setBotState(BotState.ADMIN_DB);
            adminRepository.save(adminByChatId);
        } else if (text.toLowerCase().contains("вернуться")) {
            adminByChatId.setBotState(ADMIN);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Что-нибудь еще?");
        } else if (adminRepository.findAdminByChatId(update.getMessage().getChatId()).getBotState() == BotState.ADMIN_ADD) {
            Admin admin = new Admin();
            Long id = Long.parseLong(update.getMessage().getText());
            admin.setChatId(id);
            adminRepository.save(admin);
            adminByChatId.setBotState(ADMIN);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessageForWorkWithAdmins(update, "Спасибо. Данные приняты. Если Вы указали верный ChatId, то при запуске бота, администратор увидит новое меню!");


        } else if (adminRepository.findAdminByChatId(update.getMessage().getChatId()).getBotState() == BotState.ADMIN_DB) {

            Long id = Long.parseLong(update.getMessage().getText());
            adminRepository.deleteById(id);
            adminMessageService.sendMessageForWorkWithAdmins(update, "Администратор удален!");

            adminByChatId.setBotState(ADMIN);
            adminRepository.save(adminByChatId);
        } else if (text.toLowerCase().contains("все доступные")) {
            List<Messages> all = messagesRepository.findAll();
            adminMessageService.sendMessageForWorkWithMessages(update, getTextAboutMessages(all));
        } else if (text.toLowerCase().contains("id")) {
            adminByChatId.setBotState(ADMIN_EDIT_MESSAGE_BY_ID);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Введите, пожалуйста, Id сообщения. Его можно посмотреть, если посмотреть весь список.");

        } else if (text.toLowerCase().contains("изменить по типу")) {
            adminByChatId.setBotState(ADMIN_EDIT_MESSAGE_BY_TYPE);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Введите, пожалуйста, тип сообщения. Его можно посмотреть, если посмотреть весь список.");

        } else if (adminByChatId.getBotState() == ADMIN_EDIT_MESSAGE_BY_ID) {
            id = Long.parseLong(update.getMessage().getText());
            adminByChatId.setBotState(ADMIN_EDIT_MESSAGE_BY_ID_2);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Введите новый текс сообщения/кнопки");

        } else if (adminByChatId.getBotState() == ADMIN_EDIT_MESSAGE_BY_ID_2) {
            Messages message = messagesRepository.findById(id).get();

            message.setMessegeForCleint(update.getMessage().getText());
            messagesRepository.save(message);
            adminByChatId.setBotState(ADMIN);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Изменения приняты!");
        } else if (adminByChatId.getBotState() == ADMIN_EDIT_MESSAGE_BY_TYPE) {
            type = update.getMessage().getText();
            adminByChatId.setBotState(ADMIN_EDIT_MESSAGE_BY_TYPE_2);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Введите новый текс сообщения/кнопки");

        } else if (adminByChatId.getBotState() == ADMIN_EDIT_MESSAGE_BY_TYPE_2) {
            Messages messageByMessageType = messagesRepository.findMessageByMessageType(type);
            messageByMessageType.setMessegeForCleint(update.getMessage().getText());
            messagesRepository.save(messageByMessageType);
            adminByChatId.setBotState(ADMIN);
            adminRepository.save(adminByChatId);
            adminMessageService.sendMessage(update, "Изменения приняты!");

        }

    }

    private Long sendMessageForClients(String text) {
        List<Client> all = clientRepository.findAll();
        Long count = 0l;
        for (Client client : all) {
            Long chatId = client.getChatId();
            count++;
            adminMessageService.sendMessageForClients(chatId, text);
        }
        return count;
    }


    public Client findLastClientFromBd() {
        List<Client> all = clientRepository.findAll();
        Client client = all.get(all.size() - 1);
        return client;

    }

    private List<Client> findAllClientsFromBd() {
        List<Client> all = clientRepository.findAll();
        return all;
    }

    private List<Client> findLastFiveClientsFromBd() {
        List<Client> all = clientRepository.findAll();
        List<Client> lastFive = new ArrayList<>();

        for (int i = all.size() - 5; i < all.size(); i++) {
            if (all.get(i) != null) {
                lastFive.add(all.get(i));
            }
        }
        return lastFive;
    }


    private List<Client> findLastTenClientsFromBd() {
        List<Client> all = clientRepository.findAll();
        List<Client> lastTen = new ArrayList<>();

        for (int i = all.size() - 10; i < all.size(); i++) {
            if (all.get(i) != null) {
                lastTen.add(all.get(i));
            }
        }

        return lastTen;

    }

    private String getMessageAboutClients(List<Client> list) {
        String aboutClient = "";
        for (Client client : list) {
            aboutClient = aboutClient + getMessageAboutClient(client) + "\n*****\n";
        }

        return aboutClient;
    }

    public String getMessageAboutClient(Client client) {
        String aboutClient = "";
        aboutClient = "\nId: " + client.getId() +
                "\nChatId: " + client.getChatId() +
                "\nName: " + client.getName() +
                "\nPersonal info: " + client.getPersonalInfo() +
                "\nMessage: " + client.getMessage() + "\n";
        if (client.isInstagram()) {
            aboutClient = aboutClient + "Instagram: Да\n";
        }
        if (client.isTelegram()) {
            aboutClient = aboutClient + "Telegram: Да\n";
        }
        if (client.isViber()) {
            aboutClient = aboutClient + "Viber: Да\n";
        }
        if (client.isVk()) {
            aboutClient = aboutClient + "Vk: Да\n";
        }
        if (client.isWhatsApp()) {
            aboutClient = aboutClient + "WhatsApp: Да";
        }
        return aboutClient;
    }

    public String getMessageAboutAdmins(List<Admin> list) {
        String aboutAdmin = "";
        for (Admin admin : list) {
            aboutAdmin = aboutAdmin + getMessageAboutAdmin(admin) + "\n*****\n";
        }
        return aboutAdmin;
    }

    private String getMessageAboutAdmin(Admin admin) {
        String aboutClient = "";
        aboutClient = "\nId: " + admin.getId() +
                "\nChatId: " + admin.getChatId() +
                "\nName: " + admin.getName();
        return aboutClient;
    }

    public void saveAdminInfo(Update update, Admin admin) {
        admin.setName(update.getMessage().getChat().getFirstName());
        adminRepository.save(admin);
    }

    private String getTextAboutMessages(List<Messages> list) {
        String aboutClient = "";
        for (Messages message : list) {
            aboutClient = aboutClient + getTextAboutMessage(message) + "\n*****\n";
        }

        return aboutClient;
    }

    private String getTextAboutMessage(Messages message) {
        String aboutMessage = "\nId: " + message.getId() +
                "\nType: " + message.getMessageType() +
                "\nText: " + message.getMessegeForCleint() + "\n";
        return aboutMessage;
    }
}



package by.kashlyak.integrator.integratorby.telegrambot.buttons;

import by.kashlyak.integrator.integratorby.telegrambot.entity.Client;
import by.kashlyak.integrator.integratorby.telegrambot.entity.Messages;
import by.kashlyak.integrator.integratorby.telegrambot.repository.ClientRepository;
import by.kashlyak.integrator.integratorby.telegrambot.repository.MessagesRepository;
import by.kashlyak.integrator.integratorby.telegrambot.utils.Emojis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientsButtons {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    MessagesRepository messagesRepository;
    @Autowired
    Messages messages;

    public InlineKeyboardMarkup getInlineMessageButtonsForIntegration() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton need = new InlineKeyboardButton().setText(messagesRepository.findMessageByMessageType("brief_button7").getMessegeForCleint());
        InlineKeyboardButton noNeed = new InlineKeyboardButton().setText(messagesRepository.findMessageByMessageType("brief_button8").getMessegeForCleint());
        InlineKeyboardButton oneC = new InlineKeyboardButton().setText(messagesRepository.findMessageByMessageType("brief_button9").getMessegeForCleint());
        InlineKeyboardButton myWarehouse = new InlineKeyboardButton().setText(messagesRepository.findMessageByMessageType("brief_button10").getMessegeForCleint());
        InlineKeyboardButton BitrixAmoCRM = new InlineKeyboardButton().setText(messagesRepository.findMessageByMessageType("brief_button11").getMessegeForCleint());
        InlineKeyboardButton AnotherService = new InlineKeyboardButton().setText(messagesRepository.findMessageByMessageType("brief_button11").getMessegeForCleint());


        need.setCallbackData("Need");
        noNeed.setCallbackData("NoNeed");
        oneC.setCallbackData("1C");
        myWarehouse.setCallbackData("warehouse");
        BitrixAmoCRM.setCallbackData("CRM");
        AnotherService.setCallbackData("Another");
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(need);
        keyboardButtonsRow3.add(noNeed);
        keyboardButtonsRow3.add(oneC);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(myWarehouse);
        keyboardButtonsRow4.add(BitrixAmoCRM);
        keyboardButtonsRow4.add(AnotherService);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getAdditionMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row4 = new KeyboardRow();
        KeyboardButton sendContact = new KeyboardButton();
        sendContact.setText("Поделиться контактом");
        sendContact.setRequestContact(true);
        row4.add(sendContact);
        row4.add(new KeyboardButton("Нет"));
        keyboard.add(row4);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row2.add(new KeyboardButton("Сферы применения"));
        row2.add(new KeyboardButton("Узнать стоимость"));


        row3.add(new KeyboardButton("Связаться с менеджером"));
        row1.add(new KeyboardButton("Что умеют наши боты?"));
        row1.add(new KeyboardButton("Brief"));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageButtonsForPlatformAfterChoose(Client client) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton instagram = new InlineKeyboardButton();
        if (client.isInstagram()) {
            instagram.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button1").getMessegeForCleint());
        } else {
            instagram.setText(messagesRepository.findMessageByMessageType("brief_button1").getMessegeForCleint());
        }

        InlineKeyboardButton telegram = new InlineKeyboardButton();
        if (client.isTelegram()) {
            telegram.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button2").getMessegeForCleint());
        } else {
            telegram.setText(messagesRepository.findMessageByMessageType("brief_button2").getMessegeForCleint());
        }

        InlineKeyboardButton viber = new InlineKeyboardButton();
        if (client.isViber()) {
            viber.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button3").getMessegeForCleint());
        } else {
            viber.setText(messagesRepository.findMessageByMessageType("brief_button3").getMessegeForCleint());
        }


        InlineKeyboardButton whatsApp = new InlineKeyboardButton();
        if (client.isWhatsApp()) {
            whatsApp.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button4").getMessegeForCleint());
        } else {
            whatsApp.setText(messagesRepository.findMessageByMessageType("brief_button4").getMessegeForCleint());
        }

        InlineKeyboardButton vk = new InlineKeyboardButton();
        if (client.isVk()) {
            vk.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button5").getMessegeForCleint());
        } else {
            vk.setText(messagesRepository.findMessageByMessageType("brief_button5").getMessegeForCleint());
        }

        InlineKeyboardButton nextStep = new InlineKeyboardButton();
        nextStep.setText(messagesRepository.findMessageByMessageType("brief_button6").getMessegeForCleint());


        return getInlineKeyboardMarkup(inlineKeyboardMarkup, instagram, telegram, viber, whatsApp, vk, nextStep);
    }

    public InlineKeyboardMarkup getInlineMessageButtonsForPlatformAfterChoose(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        Client clientByChatId = clientRepository.findClientByChatId(chatId);

        InlineKeyboardButton instagram = new InlineKeyboardButton();

        if (clientByChatId.isInstagram()) {
            instagram.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button1").getMessegeForCleint());
        } else {
            instagram.setText(messagesRepository.findMessageByMessageType("brief_button1").getMessegeForCleint());
        }

        InlineKeyboardButton telegram = new InlineKeyboardButton();
        if (clientByChatId.isTelegram()) {
            telegram.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button2").getMessegeForCleint());
        } else {
            telegram.setText(messagesRepository.findMessageByMessageType("brief_button2").getMessegeForCleint());
        }

        InlineKeyboardButton viber = new InlineKeyboardButton();
        if (clientByChatId.isViber()) {
            viber.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button3").getMessegeForCleint());
        } else {
            viber.setText(messagesRepository.findMessageByMessageType("brief_button3").getMessegeForCleint());
        }


        InlineKeyboardButton whatsApp = new InlineKeyboardButton();
        if (clientByChatId.isWhatsApp()) {
            whatsApp.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button4").getMessegeForCleint());
        } else {
            whatsApp.setText(messagesRepository.findMessageByMessageType("brief_button4").getMessegeForCleint());
        }

        InlineKeyboardButton vk = new InlineKeyboardButton();
        if (clientByChatId.isVk()) {
            vk.setText(Emojis.CHECK.get() + " " + messagesRepository.findMessageByMessageType("brief_button5").getMessegeForCleint());
        } else {
            vk.setText(messagesRepository.findMessageByMessageType("brief_button5").getMessegeForCleint());
        }

        InlineKeyboardButton nextStep = new InlineKeyboardButton();
        nextStep.setText(messagesRepository.findMessageByMessageType("brief_button6").getMessegeForCleint());


        return getInlineKeyboardMarkup(inlineKeyboardMarkup, instagram, telegram, viber, whatsApp, vk, nextStep);
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup(InlineKeyboardMarkup inlineKeyboardMarkup, InlineKeyboardButton instagram, InlineKeyboardButton telegram, InlineKeyboardButton viber, InlineKeyboardButton whatsApp, InlineKeyboardButton vk, InlineKeyboardButton nextStep) {
        instagram.setCallbackData("Instagram");
        telegram.setCallbackData("Telegram");
        viber.setCallbackData("Viber");
        whatsApp.setCallbackData("WhatsApp");
        vk.setCallbackData("Vk");
        nextStep.setCallbackData("Next");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(instagram);
        keyboardButtonsRow1.add(telegram);
        keyboardButtonsRow1.add(viber);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(vk);
        keyboardButtonsRow2.add(whatsApp);


        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(nextStep);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup sharedPhoneNumber() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardButton sendContact = new KeyboardButton();
        KeyboardButton none = new KeyboardButton();
        none.setText("Нет");
        sendContact.setText("Поделиться контактом");
        sendContact.setRequestContact(true);
        row.add(sendContact);
        row.add(none);
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;

    }

    public InlineKeyboardMarkup getManagerInlineApproveMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonManagerCallBack = new InlineKeyboardButton().setText("Написать менеджеру");
        buttonManagerCallBack.setUrl("http://t.me/yqpuss");


        buttonManagerCallBack.setCallbackData("buttonManagerCallBack");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonManagerCallBack);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


}

package by.kashlyak.integrator.integratorby.telegrambot.buttons;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminButtons {


    public ReplyKeyboardMarkup getAdminMainMenu() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton lookBd = new KeyboardButton("Посмотреть базу данных");
        KeyboardButton addAdmin = new KeyboardButton("Администраторы");
        KeyboardButton refacor = new KeyboardButton("Отредактировать сообщения воронки");
        KeyboardButton messageForClients = new KeyboardButton("Отправить сообщение всем клиентам");

        row.add(lookBd);
        row.add(addAdmin);
        row2.add(refacor);
        row2.add(messageForClients);
        keyboard.add(row);
        keyboard.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;

    }

    public ReplyKeyboardMarkup getAdminClientMenu() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardButton all = new KeyboardButton("Посмотреть все записи");
        KeyboardButton one = new KeyboardButton("Посмотреть последнего");
        KeyboardButton five = new KeyboardButton("Посмотреть последние 5");
        KeyboardButton ten = new KeyboardButton("Посмотреть последние 10");
        KeyboardButton back = new KeyboardButton("Вернуться");

        row.add(all);
        row.add(one);
        row2.add(five);
        row2.add(ten);
        row3.add(back);
        keyboard.add(row);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;

    }

    public ReplyKeyboard getAdminMenuForAdminDB() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        KeyboardRow row3 = new KeyboardRow();
        KeyboardButton allAdmins = new KeyboardButton("Посмотреть всех администраторов");
        KeyboardButton addAdmin = new KeyboardButton("Добавить администратора");
        KeyboardButton deleteAdmin = new KeyboardButton("Удалить администратора");
        KeyboardButton back = new KeyboardButton("Вернуться");

        row.add(allAdmins);
        row.add(addAdmin);
        row.add(deleteAdmin);
        row3.add(back);
        keyboard.add(row);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;

    }

    public ReplyKeyboard getAdminButtonsForMessageDB() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        KeyboardRow row3 = new KeyboardRow();
        KeyboardButton allAdmins = new KeyboardButton("Все доступные сообщения и кнопки для редактирования");
        KeyboardButton addAdmin = new KeyboardButton("Изменить по id");
        KeyboardButton deleteAdmin = new KeyboardButton("Изменить по типу");
        KeyboardButton back = new KeyboardButton("Вернуться");

        row.add(allAdmins);
        row.add(addAdmin);
        row.add(deleteAdmin);
        row3.add(back);
        keyboard.add(row);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

}

package by.kashlyak.integrator.integratorby.telegrambot.processor;

import by.kashlyak.integrator.integratorby.telegrambot.service.LocaleMessageService;
import by.kashlyak.integrator.integratorby.telegrambot.utils.Emojis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerProcessor implements ProcessorI {
    @Autowired
    LocaleMessageService localeMessageService;

    @Override
    public String run() {
        return localeMessageService.getMessage("reply.contactWithManager", Emojis.ARROW_DOWN.get());
    }
}

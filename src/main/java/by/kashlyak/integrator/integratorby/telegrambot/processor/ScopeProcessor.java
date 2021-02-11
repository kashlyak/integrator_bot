package by.kashlyak.integrator.integratorby.telegrambot.processor;

import by.kashlyak.integrator.integratorby.telegrambot.service.LocaleMessageService;
import by.kashlyak.integrator.integratorby.telegrambot.utils.Emojis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScopeProcessor implements ProcessorI {
    @Autowired
    LocaleMessageService localeMessageService;

    @Override
    public String run() {
        return localeMessageService.getMessage("reply.fieldOfApplication", Emojis.CART.get(),
                Emojis.MONEYBAG.get(), Emojis.SHIELD.get(), Emojis.PILL.get(), Emojis.HOMES.get(), Emojis.BENTO.get(),
                Emojis.DESKTOP.get(), Emojis.RAILROAD_TRACK.get());
    }
}

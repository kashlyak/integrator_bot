package by.kashlyak.integrator.integratorby.telegrambot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;


public enum Emojis {

    CHECK(":heavy_check_mark:"),
    CART(":shopping_trolley:"),
    MONEYBAG(":moneybag:"),
    SHIELD(":shield:"),
    PILL(":pill:"),
    HOMES(":house_buildings:"),
    BENTO(":bento:"),
    DESKTOP(":desktop_computer:"),
    RAILROAD_TRACK(":railway_track:"),
    BAR_CHART(":bar_chart:"),
    CALLING(":calling:"),
    ENVELOPE_WITH_ARROW(":envelope_with_arrow:"),
    DOLLAR(":dollar:"),
    CREDIT_CARD(":credit_card:"),
    MAG_RIGHT(":mag_right:"),
    SHOPPING_BAGS(":shopping_bags:"),
    GEAR(":gear:"),
    BOOKS(":books:"),
    GIFT(":gift:"),
    ROBOTFACE(":robot_face:"),
    ARROW_DOWN(":arrow_down:"),
    TOOLS(":hammer_and_wrench:");

    private String value;
    public String get() {
        return EmojiParser.parseToUnicode(value);
    }
    Emojis(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
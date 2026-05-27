package uz.pdp.currancy_bot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.currancy_bot.model.enums.Language;
import uz.pdp.currancy_bot.model.Member;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonMaker {

    public InlineKeyboardMarkup settingButton(Member member) {
        String from = member.getFrom().name();
        String to = member.getTo().name();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton uzFrom = new InlineKeyboardButton();
        uzFrom.setText(from.equals("UZS") ? "UZS ✅" : "UZS");
        uzFrom.setCallbackData("from_uzs");
        InlineKeyboardButton uzTo = new InlineKeyboardButton();
        uzTo.setText(to.equals("UZS") ? "UZS ✅" : "UZS");
        uzTo.setCallbackData("to_uzs");


        InlineKeyboardButton usdFrom = new InlineKeyboardButton();
        usdFrom.setText(from.equals("USD") ? "USD ✅" : "USD");
        usdFrom.setCallbackData("from_usd");
        InlineKeyboardButton usdTo = new InlineKeyboardButton();
        usdTo.setText(to.equals("USD") ? "USD ✅" : "USD");
        usdTo.setCallbackData("to_usd");


        InlineKeyboardButton rubFrom = new InlineKeyboardButton();
        rubFrom.setText(from.equals("RUB") ? "RUB ✅" : "RUB");
        rubFrom.setCallbackData("from_rub");
        InlineKeyboardButton rubTo = new InlineKeyboardButton();
        rubTo.setText(to.equals("RUB") ? "RUB ✅" : "RUB");
        rubTo.setCallbackData("to_rub");


        InlineKeyboardButton euroFrom = new InlineKeyboardButton();
        euroFrom.setText(from.equals("EUR") ? "EUR ✅" : "EUR");
        euroFrom.setCallbackData("from_eur");
        InlineKeyboardButton euroTo = new InlineKeyboardButton();
        euroTo.setText(to.equals("EUR") ? "EUR ✅" : "EUR");
        euroTo.setCallbackData("to_eur");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(uzFrom);
        row1.add(uzTo);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(usdFrom);
        row2.add(usdTo);
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(rubFrom);
        row3.add(rubTo);
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(euroFrom);
        row4.add(euroTo);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    /*public ReplyKeyboardMarkup email() {
        KeyboardRow button = new KeyboardRow();
        button.add("📧 Email");
        List<KeyboardRow> row = new ArrayList<>();
        row.add(button);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(row);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }*/

    public ReplyKeyboard mainMenu(Member member) {
        Language lang = member.getLanguage();

        String settingsText = switch (lang) {
            case RUSSIAN -> "⚙️ Настройки";
            case ENGLISH -> "⚙️ Settings";
            default -> "⚙️ Valyutani tanlash";
        };

        String langText = switch (lang) {
            case RUSSIAN -> "🌐 Выбрать язык";
            case ENGLISH -> "🌐 Language";
            default -> "🌐 Tilni tanlash";
        };

        KeyboardRow row = new KeyboardRow();
        row.add(settingsText);
        row.add(langText);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }


    public ReplyKeyboard languageButton() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton uz = new InlineKeyboardButton("🇺🇿 O'zbekcha");
        uz.setCallbackData("lang_uzb");

        InlineKeyboardButton en = new InlineKeyboardButton("🇺🇸 English");
        en.setCallbackData("lang_eng");

        InlineKeyboardButton ru = new InlineKeyboardButton("🇷🇺 Русский");
        ru.setCallbackData("lang_rus");

        rows.add(List.of(uz));
        rows.add(List.of(en));
        rows.add(List.of(ru));

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
}

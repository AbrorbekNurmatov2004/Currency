package uz.pdp.currancy_bot.utils;

import uz.pdp.currancy_bot.model.enums.Language;

public class Utility {

    public static boolean setting(String text, Language language) {
        return switch (language) {
            case RUSSIAN -> "⚙️ Настройки".equals(text);
            case ENGLISH -> "⚙️ Settings".equals(text);
            default -> "⚙️ Valyutani tanlash".equals(text);
        };
    }

    public static boolean language(String text, Language lang) {
        return switch (lang) {
            case RUSSIAN -> "🌐 Выбрать язык".equals(text);
            case ENGLISH -> "🌐 Language".equals(text);
            default -> "🌐 Tilni tanlash".equals(text);
        };
    }

}

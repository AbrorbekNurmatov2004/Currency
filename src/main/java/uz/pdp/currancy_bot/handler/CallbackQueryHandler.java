package uz.pdp.currancy_bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.pdp.currancy_bot.service.TelegramService;

@Controller
@RequiredArgsConstructor
public class CallbackQueryHandler {

    private final TelegramService service;

    public void handle(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        String data = callbackQuery.getData();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        if (data.startsWith("from")) {
            String from = data.replace("from_", "").toUpperCase();
            service.updateFrom(chatId, from, messageId);
        } else if (data.startsWith("to")) {
            String to = data.replace("to_", "").toUpperCase();
            service.updateTo(chatId, to, messageId);
        } else if (data.startsWith("lang")) {
            String lang = data.replace("lang_", "").toUpperCase();
            service.langSetting(chatId, lang, messageId);
        }
    }
}


package uz.pdp.currancy_bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.currancy_bot.model.enums.Language;
import uz.pdp.currancy_bot.model.Member;
import uz.pdp.currancy_bot.service.TelegramService;
import uz.pdp.currancy_bot.utils.Utility;

@Controller
@RequiredArgsConstructor
public class MessageHandler {

    private final TelegramService service;

    public void handle(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        Member member = service.currentMember(chatId);
        Language language = member.getLanguage();

        if ("/start".equals(text)) {
            service.sendWelcome(message);
        } else if (text != null && text.matches("\\d+")) {
            service.convertResult(text, chatId);
        } else if (Utility.setting(text, language)) {
            service.sendSettings(chatId);
        } else if (Utility.language(text, language)) {
            service.chooseLanguage(chatId);
        } else {
            service.deleteMessage(message.getMessageId(), chatId);
        }
    }
}
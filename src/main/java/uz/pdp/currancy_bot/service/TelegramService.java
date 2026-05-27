package uz.pdp.currancy_bot.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.currancy_bot.bot.ConverterBot;
import uz.pdp.currancy_bot.bot.ButtonMaker;
import uz.pdp.currancy_bot.model.enums.Currency;
import uz.pdp.currancy_bot.model.dto.CurrencyResponse;
import uz.pdp.currancy_bot.model.enums.Language;
import uz.pdp.currancy_bot.model.Member;
import uz.pdp.currancy_bot.repository.TelegramRepository;
import java.time.LocalDateTime;

@Service
public class TelegramService {

    private final TelegramRepository repository;
    private final ConverterBot converterBot;
    private final RemoteApiService apiService;
    private final ButtonMaker buttonMaker;

    public TelegramService(TelegramRepository repository, @Lazy ConverterBot converterBot, RemoteApiService apiService, ButtonMaker buttonMaker) {
        this.repository = repository;
        this.converterBot = converterBot;
        this.apiService = apiService;
        this.buttonMaker = buttonMaker;
    }

    public void sendWelcome(Message message) {
        String chatId = message.getChatId().toString();
        Member member = createOrGetMember(chatId, message.getFrom().getFirstName());

        String welcome = """
                👋 Salom %s!
                Ushbu bot valutlarni konvertatsiya qilib beradi.
                Kerakli qiymatni yuboring.
                ---------
                ⚙️ Kerakli sozlamalarni pastdagi tugmalardan tanlang:
                """.formatted(member.getName());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(welcome);
        sendMessage.setReplyMarkup(buttonMaker.mainMenu(member));
        converterBot.sendMessage(sendMessage);
    }

    public void deleteMessage(Integer messageId, String chatId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(messageId);
        deleteMessage.setChatId(chatId);
        converterBot.deleteMessage(deleteMessage);
    }

    public void convertResult(String text, String chatId) {
        Member member = currentMember(chatId);
        Language language = member.getLanguage();

        String title = switch (language) {
            case RUSSIAN -> "💱 Результат конвертации:";
            case ENGLISH -> "💱 Conversion result:";
            default -> "💱 Konvertatsiya natijasi:";
        };

        Double toRate = convert(text, member);
        String message = """
                %s
                
                💰 %s %s  ➡️  %.2f %s
                """.formatted(title, text, member.getFrom(), toRate, member.getTo());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        converterBot.sendMessage(sendMessage);
    }

    public Double convert(String text, Member member) {
        Double value = Double.parseDouble(text);
        if (member.getTo().equals(member.getFrom())) {
            return value;
        } else if (member.getFrom().equals(Currency.UZS)) {
            CurrencyResponse response = apiService.retrieveCurrency(member.getTo().name());
            Double to = Double.parseDouble(response.getRate());
            return value / to;
        } else if (member.getTo().equals(Currency.UZS)) {
            CurrencyResponse response = apiService.retrieveCurrency(member.getFrom().name());
            Double from = Double.parseDouble(response.getRate());
            return value * from;
        } else {
            CurrencyResponse response = apiService.retrieveCurrency(member.getFrom().name());
            Double fromRate = Double.parseDouble(response.getRate());
            Double sum = value * fromRate;

            CurrencyResponse toResponse = apiService.retrieveCurrency(member.getTo().name());
            Double toRate = Double.parseDouble(toResponse.getRate());
            return sum / toRate;
        }
    }

    public void sendSettings(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Konverterni sozlang👇");
        sendMessage.setReplyMarkup(buttonMaker.settingButton(currentMember(chatId)));
        converterBot.sendMessage(sendMessage);
    }

    public void updateFrom(String chatId, String from, Integer messageId) {
        Member member = currentMember(chatId);
        member.setFrom(Currency.valueOf(from));
        repository.save(member);
        refreshSetting(chatId, messageId);
    }

    public void updateTo(String chatId, String to, Integer messageId) {
        Member member = currentMember(chatId);
        member.setTo(Currency.valueOf(to));
        repository.save(member);
        refreshSetting(chatId, messageId);
    }

    public void refreshSetting(String chatId, Integer messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText("Konverterni sozlang👇");
        editMessage.setReplyMarkup(buttonMaker.settingButton(currentMember(chatId)));
        converterBot.editMessage(editMessage);
    }

    public void chooseLanguage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Tilni sozlang👇");
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(buttonMaker.languageButton());
        converterBot.sendMessage(sendMessage);
    }

    public void langSetting(String chatId, String lang, Integer messageId) {
        Language newLang = switch (lang) {
            case "ENG" -> Language.ENGLISH;
            case "RUS" -> Language.RUSSIAN;
            default -> Language.UZBEK;
        };
        Member member = currentMember(chatId);
        member.setLanguage(newLang);
        repository.save(member);

        String text = switch (newLang) {
            case RUSSIAN -> "✅ Язык изменен на русский!";
            case ENGLISH -> "✅ Language changed to English!";
            default -> "✅ Til o'zbekchaga o'zgartirildi!";
        };

        EditMessageText edit = new EditMessageText();
        edit.setChatId(chatId);
        edit.setMessageId(messageId);
        edit.setText(text);
        converterBot.editMessage(edit);
        SendMessage menu = new SendMessage(chatId, "#########################");
        menu.setReplyMarkup(buttonMaker.mainMenu(member));
        converterBot.sendMessage(menu);
    }

    public Member currentMember(String chatId) {
        return createOrGetMember(chatId, null);
    }

    private Member createOrGetMember(String chatId, String name) {
        return repository.findByChatId(chatId).orElseGet(() -> {
            Member member = new Member();
            member.setName(name);
            member.setChatId(chatId);
            member.setCreatedAt(LocalDateTime.now());
            member.setUpdatedAt(LocalDateTime.now());
            return repository.save(member);
        });
    }
}

package uz.pdp.currancy_bot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.currancy_bot.bot.ConverterBot;

@Configuration
@Slf4j
public class BotInitializer {

    @Bean
    public TelegramBotsApi telegramBotsApi(ConverterBot bot) {
        TelegramBotsApi api = null;
        try {
            api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
            log.info("Telegram bots api successfully registered");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        return api;
    }

}

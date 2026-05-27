package uz.pdp.currancy_bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class AppConfig {

    @Value("${application.bot-token:null}")
    public String token;

    @Value("${application.bot-username:null}")
    private String username;

    @Value("${application.centeral-bank-api:null}")
    private String centerBankApi;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}

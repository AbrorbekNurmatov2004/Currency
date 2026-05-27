package uz.pdp.currancy_bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.currancy_bot.config.AppConfig;
import uz.pdp.currancy_bot.model.dto.CurrencyResponse;
import uz.pdp.currancy_bot.utils.ErrorConstants;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteApiService {

    private final AppConfig config;
    private final RestTemplate restTemplate;

    public CurrencyResponse retrieveCurrency(String currency) {
        ResponseEntity<CurrencyResponse[]> response = restTemplate.getForEntity(config.getCenterBankApi() + currency + "/", CurrencyResponse[].class);
        CurrencyResponse[] body = response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            return body[0];
        } else {
            log.error(ErrorConstants.SERVER_ERROR);
            throw new RuntimeException("Markaziy bank ishlamayapti");
        }
    }

}

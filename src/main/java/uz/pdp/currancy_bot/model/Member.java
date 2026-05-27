package uz.pdp.currancy_bot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uz.pdp.currancy_bot.model.enums.Currency;
import uz.pdp.currancy_bot.model.enums.Language;
import uz.pdp.currancy_bot.model.enums.UserState;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Member {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private String code;
    private String chatId;
    private UserState state;
    private Language language = Language.UZBEK;
    private Currency from = Currency.UZS;
    private Currency to = Currency.UZS;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

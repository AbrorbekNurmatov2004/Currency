package uz.pdp.currancy_bot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.pdp.currancy_bot.model.Member;

import java.util.Optional;

public interface TelegramRepository extends MongoRepository<Member,String> {

    Optional<Member> findByChatId(String chatId);
}

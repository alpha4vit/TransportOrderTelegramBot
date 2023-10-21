package by.gurinovich.PricePodschet.config;

import by.gurinovich.PricePodschet.bot.PricePodshetBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class Initializer {
    private final PricePodshetBot pricePodshetBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init(){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(pricePodshetBot);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

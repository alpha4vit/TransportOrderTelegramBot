package by.gurinovich.PricePodschet.models.buttons;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Component
public interface BotCommands {
    List LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Начать работу!"),
            new BotCommand("/passenger", "Стать пассажиром!"),
            new BotCommand("/cargo", "Передать посылку!"),
            new BotCommand("/individual", "Индивидуальный переезд!")
    );
}

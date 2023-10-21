package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.buttons.CargoButtons;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.MessageType;
import by.gurinovich.PricePodschet.utils.states.BotState;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import by.gurinovich.PricePodschet.utils.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class CallbackHandler {

    private final HTMLParser htmlParser;
    private final CargoHandler cargoHandler;
    private final UserService userService;
    private final MessageHandler messageHandler;

    public SendMessage answer(String message, Long chatId, String username){
        SendMessage result = new SendMessage();
        System.out.println(message);
        if (message.startsWith("CARGOTYPE"))
            result = cargoHandler.answer(message, chatId);
        else if (message.startsWith("PASSENGER")){

        }
        else if (message.startsWith("INDIVIDUAL")){

        }
        else {
            switch (OrderType.valueOf(message)) {
                case CARGO_ORDER -> {
                    result.setText(htmlParser.readHTML("src/main/resources/static/cargo/cargo.html"));
                    result.setReplyMarkup(CargoButtons.createChooseTypeKeyboard());
                    userService.setState(chatId, BotState.CARGO_ORDER);
                }
                case PASSENGER_ORDER -> {
                    result.setText(htmlParser.readHTML("src/main/resources/static/passenger/passenger.html"));
                    userService.setState(chatId, BotState.PASSENGER_ORDER);
                }
                case INDIVIDUAL_ORDER -> {
                    result.setText(htmlParser.readHTML("src/main/resources/static/individual/individual.html"));
                    userService.setState(chatId, BotState.INDIVIDUAL_ORDER);
                }
                case NEW_ORDER -> {
                    result = messageHandler.answer("/start", chatId, username);
                    userService.setState(chatId, BotState.START);
                }
            }
        }
        result.setParseMode("HTML");
        return result;
    }
}

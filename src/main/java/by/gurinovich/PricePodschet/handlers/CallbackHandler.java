package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.buttons.CargoButtons;
import by.gurinovich.PricePodschet.models.buttons.IndividualButtons;
import by.gurinovich.PricePodschet.services.orders.CityService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.MessageType;
import by.gurinovich.PricePodschet.utils.individual.IndividualActions;
import by.gurinovich.PricePodschet.utils.passengers.PassengerActions;
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
    private final PassengerHandler passengerHandler;
    private final IndividualHandler individualHandler;

    public SendMessage answer(String message, Long chatId, String username, String name){
        SendMessage result = new SendMessage();
        if (message.startsWith("CARGOTYPE"))
            result = cargoHandler.answer(message, chatId);
        else if (message.startsWith("PASSENGERTYPE")){
            result = passengerHandler.answer(message, chatId);
        }
        else if (message.startsWith("INDIVIDUALTYPE")){
            result = individualHandler.answer(message, chatId);
        }
        else {
            switch (OrderType.valueOf(message)) {
                case CARGO_ORDER -> {
                    result.setText(htmlParser.readHTML("src/main/resources/static/cargo/cargo.html"));
                    result.setReplyMarkup(CargoButtons.createChooseTypeKeyboard());
                    userService.setState(chatId, BotState.CARGO_ORDER);
                }
                case PASSENGER_ORDER -> {
                    userService.setState(chatId, BotState.PASSENGER_ORDER);
                    result = passengerHandler.answer(PassengerActions.PASSENGERTYPE_ROUTE.name(), chatId);
                }
                case INDIVIDUAL_ORDER -> {
                    userService.setState(chatId, BotState.INDIVIDUAL_ORDER);
                    result = individualHandler.answer(IndividualActions.INDIVIDUALTYPE_ROUTE.name(), chatId);
                }
                case NEW_ORDER -> {
                    result = messageHandler.answer("/start", chatId, username, name);
                    userService.setState(chatId, BotState.START);
                }
            }
        }
        result.setParseMode("HTML");
        return result;
    }
}

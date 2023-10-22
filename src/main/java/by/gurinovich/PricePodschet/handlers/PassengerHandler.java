package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.buttons.GeneralActionButtons;
import by.gurinovich.PricePodschet.models.buttons.PassengerButtons;
import by.gurinovich.PricePodschet.services.orders.OrderService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.OrderType;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import by.gurinovich.PricePodschet.utils.passengers.PassengerActions;
import by.gurinovich.PricePodschet.utils.states.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class PassengerHandler {

    private final HTMLParser htmlParser;
    private final UserService userService;
    private final OrderService orderService;

    public SendMessage answer(String message, Long chatId){
        SendMessage result = new SendMessage();

        switch (PassengerActions.valueOf(message)){
            case PASSENGERTYPE_ROUTE -> {
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/cityChoose.html"));
                userService.setState(chatId, BotState.PASSENGER_ORDER_ROUTE);
            }
//            case PASSENGERTYPE_SKIP_COMMENT -> {
//                userService.setState(chatId, BotState.CONFIRMATION);
//                Order order = orderService.getUnconfirmedOrder(chatId, OrderType.PASSENGER_ORDER);
//                result.setReplyMarkup(PassengerButtons.createOrderConfirmKeyboard());
//                result.setText(String.format(htmlParser.readHTML("src/main/resources/static/passenger/orderConfirmation.html"),
//                        order.getValue(),
//                        order.getDeparture(),
//                        order.getDestination(),
//                        order.getComment()));
//            }
            case PASSENGERTYPE_CONFIRMATION -> {
                userService.setState(chatId, BotState.SEND_MESSAGE);
                orderService.confirm(chatId, OrderType.PASSENGER_ORDER);
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/orderConfirmed.html"));
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
            case PASSENGERTYPE_DECLINE -> {
                orderService.deleteUncofirmedByChatIdAndType(chatId, OrderType.PASSENGER_ORDER);
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/orderDeclined.html"));
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
        }

        return result;
    }
}

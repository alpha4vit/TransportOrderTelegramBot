package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.buttons.CargoButtons;
import by.gurinovich.PricePodschet.models.buttons.GeneralActionButtons;
import by.gurinovich.PricePodschet.models.buttons.IndividualButtons;
import by.gurinovich.PricePodschet.models.buttons.PassengerButtons;
import by.gurinovich.PricePodschet.services.orders.OrderService;
import by.gurinovich.PricePodschet.services.orders.CityService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.OrderType;
import by.gurinovich.PricePodschet.utils.states.BotState;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor

public class MessageHandler {

    private final HTMLParser htmlParser;
    private final UserService userService;
    private final OrderService orderService;
    private final CityService cityService;

    public SendMessage answer(String message,Long chatId, String username, String name){
        SendMessage result = new SendMessage();
        switch (message){
            case "/start" -> {
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/welcome.html"));
                result.setReplyMarkup(GeneralActionButtons.actionChooseButtons());
                if (userService.save(chatId, username, name) == null){
                    userService.setState(chatId, BotState.START);
                    userService.clearOrders(chatId);
                }
            }
            case "/help" -> {
                System.out.println("HELP WINDOW"); // TODO
            }
        }

        BotState currentState = userService.getByChatId(chatId).getBotState();
        switch (currentState){
            case CARGO_ORDER_ANOTHER -> {
                orderService.setValue(chatId, message, OrderType.CARGO_ORDER);
                userService.setState(chatId, BotState.CARGO_ORDER_ROUTE);
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/cityChoose.html"));
            }
            case CARGO_ORDER_ROUTE -> {
                if (cityService.setRouteToOrder(chatId, message, OrderType.CARGO_ORDER)) {
                    userService.setState(chatId, BotState.CARGO_ORDER_COMMENT);
                    result.setText(htmlParser.readHTML("src/main/resources/static/messages/commentOrder.html"));
                    result.setReplyMarkup(CargoButtons.createSkipCommentKeyboard());
                }
                else{
                    result.setText("Было введено некорректное значение или таких городов нет в нашем списке. Попробуйте еще раз"); //TODO сделать список выпадающих городов
                }
            }
            case CARGO_ORDER_COMMENT -> {
                orderService.setComment(chatId, message, OrderType.CARGO_ORDER);
                userService.setState(chatId, BotState.CONFIRMATION);
                Order order = orderService.getUnconfirmedOrder(chatId, OrderType.CARGO_ORDER);
                result.setReplyMarkup(CargoButtons.createOrderConfirmKeyboard());
                result.setText(String.format(htmlParser.readHTML("src/main/resources/static/cargo/orderConfirmation.html"),
                        order.getValue(),
                        order.getDeparture(),
                        order.getDestination(),
                        order.getComment()));
            }
            case PASSENGER_ORDER_ROUTE -> {
                if (cityService.setRouteToOrder(chatId, message, OrderType.PASSENGER_ORDER)){
                    userService.setState(chatId, BotState.PASSENGER_ORDER_COMMENT);
                    result.setText(htmlParser.readHTML("src/main/resources/static/passenger/orderComment.html"));
                    //result.setReplyMarkup(PassengerButtons.createSkipCommentKeyboard());
                }
                else{
                    result.setText("Было введено некорректное значение или таких городов нет в нашем списке. Попробуйте еще раз"); //TODO сделать список выпадающих городов
                }
            }
            case PASSENGER_ORDER_COMMENT -> {
                orderService.setComment(chatId, message, OrderType.PASSENGER_ORDER);
                userService.setState(chatId, BotState.CONFIRMATION);
                Order order = orderService.getUnconfirmedOrder(chatId, OrderType.PASSENGER_ORDER);
                result.setReplyMarkup(PassengerButtons.createOrderConfirmKeyboard());
                result.setText(String.format(htmlParser.readHTML("src/main/resources/static/passenger/orderConfirmation.html"),
                        order.getDeparture(),
                        order.getDestination(),
                        order.getComment()));
            }
            case INDIVIDUAL_ORDER_ROUTE -> {
                if (cityService.setRouteToOrder(chatId, message, OrderType.INDIVIDUAL_ORDER)){
                    userService.setState(chatId, BotState.INDIVIDUAL_OWNER_CHECK);
                    result.setText(htmlParser.readHTML("src/main/resources/static/individual/checkOwnerOfIndividual.html"));
                    result.setReplyMarkup(IndividualButtons.createConfirmOwnerKeyboard());
                }
                else{
                    result.setText("Было введено некорректное значение или таких городов нет в нашем списке. Попробуйте еще раз"); //TODO сделать список выпадающих городов
                }

            }
            case INDIVIDUAL_OWNER_COMMENT -> {
                orderService.setComment(chatId, message, OrderType.INDIVIDUAL_ORDER);
                userService.setState(chatId, BotState.CONFIRMATION);
                Order order = orderService.getUnconfirmedOrder(chatId, OrderType.INDIVIDUAL_ORDER);
                result.setReplyMarkup(IndividualButtons.createOrderConfirmKeyboard());
                result.setText(String.format(htmlParser.readHTML("src/main/resources/static/individual/orderConfirmation.html"),
                        order.isWithOwner() ? "Да" : "Нет",
                        order.getDeparture(),
                        order.getDestination(),
                        order.getComment()));
            }
        }
        result.setParseMode("HTML");
        return result;
    }
}

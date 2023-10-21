package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.CargoOrder;
import by.gurinovich.PricePodschet.models.buttons.CargoButtons;
import by.gurinovich.PricePodschet.models.buttons.GeneralActionButtons;
import by.gurinovich.PricePodschet.services.orders.CargoOrderService;
import by.gurinovich.PricePodschet.services.orders.CityService;
import by.gurinovich.PricePodschet.services.users.UserService;
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
    private final CargoOrderService cargoOrderService;
    private final CityService cityService;

    public SendMessage answer(String message,Long chatId, String username){
        SendMessage result = new SendMessage();
        switch (message){
            case "/start" -> {
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/welcome.html"));
                result.setReplyMarkup(GeneralActionButtons.actionChooseButtons());
                if (userService.save(chatId, username) == null){
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
                cargoOrderService.setValue(chatId, message);
                userService.setState(chatId, BotState.CARGO_ORDER_ROUTE);
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/cityChoose.html"));
            }
            case CARGO_ORDER_ROUTE -> {
                boolean setted = cityService.setRouteToOrder(chatId, message);
                if (setted) {
                    userService.setState(chatId, BotState.CONFIRMATION);
                    CargoOrder cargoOrder = cargoOrderService.getUnconfirmedCargoOrder(chatId);
                    result.setReplyMarkup(CargoButtons.createOrderConfirmKeyboard());
                    result.setText(String.format(htmlParser.readHTML("src/main/resources/static/messages/orderConfirmation.html"), cargoOrder.getUser().getName(),
                            cargoOrder.getValue(),
                            cargoOrder.getDeparture(),
                            cargoOrder.getDestination()));
                }
                else{
                    result.setText("Было введено некорректное значение или таких городов нет в нашем списке. Попробуйте еще раз");
                }
            }
        }

        result.setParseMode("HTML");
        return result;
    }
}

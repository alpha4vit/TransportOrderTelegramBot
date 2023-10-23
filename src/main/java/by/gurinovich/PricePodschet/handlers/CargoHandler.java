package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.buttons.CargoButtons;
import by.gurinovich.PricePodschet.models.buttons.GeneralActionButtons;
import by.gurinovich.PricePodschet.services.orders.CityService;
import by.gurinovich.PricePodschet.services.orders.OrderService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.OrderType;
import by.gurinovich.PricePodschet.utils.states.BotState;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import by.gurinovich.PricePodschet.utils.cargo.CargoActions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class CargoHandler {
    private final HTMLParser htmlParser;
    private final UserService userService;
    private final OrderService orderService;
    private final CityService cityService;

    public SendMessage answer(String message, Long chatId) {

        SendMessage result = new SendMessage();
        result.setText(htmlParser.readHTML("src/main/resources/static/messages/cityChoose.html"));
        BotState state = BotState.CARGO_ORDER_ROUTE;
        result.setReplyMarkup(CargoButtons.createButtonForGetListOfCities());

        switch (CargoActions.valueOf(message)) {
            case CARGOTYPE_BOX_S -> orderService.setValue(chatId, "Коробка/чемодан S", OrderType.CARGO_ORDER);
            case CARGOTYPE_BOX_M -> orderService.setValue(chatId, "Коробка/чемодан M", OrderType.CARGO_ORDER);
            case CARGOTYPE_BOX_L -> orderService.setValue(chatId, "Коробка/чемодан L", OrderType.CARGO_ORDER);
            case CARGOTYPE_BOX_XL -> orderService.setValue(chatId, "Коробка/чемодан XL", OrderType.CARGO_ORDER);
            case CARGOTYPE_DOCUMENT -> orderService.setValue(chatId, "Документы", OrderType.CARGO_ORDER);
            case CARGOTYPE_ANOTHER -> {
                result.setText(htmlParser.readHTML("src/main/resources/static/cargo/anotherType.html"));
                state = BotState.CARGO_ORDER_ANOTHER;
                result.setReplyMarkup(null);
            }
            case CARGOTYPE_SKIP_COMMENT -> {
                orderService.setComment(chatId, "", OrderType.CARGO_ORDER);
                userService.setState(chatId, BotState.CONFIRMATION);
                Order order = orderService.getUnconfirmedOrder(chatId, OrderType.CARGO_ORDER);
                result.setReplyMarkup(CargoButtons.createOrderConfirmKeyboard());
                result.setText(String.format(htmlParser.readHTML("src/main/resources/static/cargo/orderConfirmation.html"),
                        order.getValue(),
                        order.getDeparture(),
                        order.getDestination(),
                        order.getComment()));
            }
            case CARGOTYPE_CONFIRMATION -> {
                orderService.confirm(chatId, OrderType.CARGO_ORDER);
                state = BotState.SEND_MESSAGE;
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/orderConfirmed.html"));
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
            case CARGOTYPE_DECLINE -> {
                orderService.deleteUncofirmedByChatIdAndType(chatId, OrderType.CARGO_ORDER);
                state = BotState.START;
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/orderDeclined.html"));
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
            case CARGOTYPE_LIST_OF_CITIES -> {
                result.setText(cityService.getStringOfAvailableCities());
                result.setReplyMarkup(null);
            }
        }
        userService.setState(chatId, state);
        return result;
    }
}

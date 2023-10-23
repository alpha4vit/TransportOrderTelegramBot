package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.buttons.GeneralActionButtons;
import by.gurinovich.PricePodschet.models.buttons.IndividualButtons;
import by.gurinovich.PricePodschet.services.orders.CityService;
import by.gurinovich.PricePodschet.services.orders.OrderService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.OrderType;
import by.gurinovich.PricePodschet.utils.individual.IndividualActions;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import by.gurinovich.PricePodschet.utils.states.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class IndividualHandler {

    private final HTMLParser htmlParser;
    private final OrderService orderService;
    private final UserService userService;
    private final CityService cityService;

    public SendMessage answer(String message, Long chatId) {
        SendMessage result = new SendMessage();
        switch (IndividualActions.valueOf(message)) {
            case INDIVIDUALTYPE_ROUTE -> {
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/cityChoose.html"));
                System.out.println("dsadadadas");
                userService.setState(chatId, BotState.INDIVIDUAL_ORDER_ROUTE);
                result.setReplyMarkup(IndividualButtons.createButtonForGetListOfCities());
            }
            case INDIVIDUALTYPE_APPLY_OWNER, INDIVIDUALTYPE_DECLINE_OWNER -> {
                if (IndividualActions.INDIVIDUALTYPE_APPLY_OWNER.name().equals(message))
                    orderService.setWithOwner(chatId, OrderType.INDIVIDUAL_ORDER);
                result.setText(htmlParser.readHTML("src/main/resources/static/individual/orderComment.html"));
                userService.setState(chatId, BotState.INDIVIDUAL_OWNER_COMMENT);
            }
            case INDIVIDUALTYPE_CONFIRMATION -> {
                userService.setState(chatId, BotState.SEND_MESSAGE);
                orderService.confirm(chatId, OrderType.INDIVIDUAL_ORDER);
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/orderConfirmed.html"));
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
            case INDIVIDUALTYPE_DECLINE -> {
                orderService.deleteUncofirmedByChatIdAndType(chatId, OrderType.INDIVIDUAL_ORDER);
                result.setText(htmlParser.readHTML("src/main/resources/static/messages/orderConfirmed.html"));
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
            case INDIVIDUALTYPE_LIST_OF_CITIES -> {
                result.setText(cityService.getStringOfAvailableCities());
            }
        }
        return result;
    }
}

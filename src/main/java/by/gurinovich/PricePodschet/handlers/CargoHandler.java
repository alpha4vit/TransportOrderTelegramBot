package by.gurinovich.PricePodschet.handlers;

import by.gurinovich.PricePodschet.models.buttons.GeneralActionButtons;
import by.gurinovich.PricePodschet.services.orders.CargoOrderService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.states.BotState;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import by.gurinovich.PricePodschet.utils.cargo.CargoTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class CargoHandler {
    private final HTMLParser htmlParser;
    private final UserService userService;
    private final CargoOrderService cargoOrderService;

    public SendMessage answer(String message, Long chatId) {

        SendMessage result = new SendMessage();
        result.setText(htmlParser.readHTML("src/main/resources/static/messages/cityChoose.html"));
        BotState state = BotState.CARGO_ORDER_ROUTE;

        switch (CargoTypes.valueOf(message)) {
            case CARGOTYPE_BOX_S -> cargoOrderService.setValue(chatId, "Коробка/чемодан S");
            case CARGOTYPE_BOX_M -> cargoOrderService.setValue(chatId, "Коробка/чемодан M");
            case CARGOTYPE_BOX_L -> cargoOrderService.setValue(chatId, "Коробка/чемодан L");
            case CARGOTYPE_BOX_XL -> cargoOrderService.setValue(chatId, "Коробка/чемодан XL");
            case CARGOTYPE_DOCUMENT -> cargoOrderService.setValue(chatId, "Документы");
            case CARGOTYPE_ANOTHER -> {
                result.setText(htmlParser.readHTML("src/main/resources/static/cargo/anotherType.html"));
                state = BotState.CARGO_ORDER_ANOTHER;
            }
            case CARGOTYPE_CONFIRMATION -> {
                cargoOrderService.confirm(chatId);
                state = BotState.SEND_MESSAGE;
                result.setText("Успешно подтверждено!");
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
            case CARGOTYPE_DECLINE -> {
                cargoOrderService.deleteUncofirmedByChatId(chatId);
                state = BotState.START;
                result.setText("Успешно отменено!");
                result.setReplyMarkup(GeneralActionButtons.createNewOrderButtons());
            }
        }
        userService.setState(chatId, state);
        return result;
    }
}

package by.gurinovich.PricePodschet.models.buttons;

import by.gurinovich.PricePodschet.utils.passengers.PassengerActions;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class PassengerButtons {
    private static final InlineKeyboardButton CONFIRM = new InlineKeyboardButton("Подтвердить!");
    private static final InlineKeyboardButton DECLINE = new InlineKeyboardButton("Отменить!");

    private static final InlineKeyboardButton SKIP_COMMENT = new InlineKeyboardButton("Пропустить!");

    private static final InlineKeyboardButton GET_LIST_OF_CITIES = new InlineKeyboardButton("Получить список доступных городов!");

    static {
        CONFIRM.setCallbackData(PassengerActions.PASSENGERTYPE_CONFIRMATION.name());
        DECLINE.setCallbackData(PassengerActions.PASSENGERTYPE_DECLINE.name());
        SKIP_COMMENT.setCallbackData(PassengerActions.PASSENGERTYPE_SKIP_COMMENT.name());

        GET_LIST_OF_CITIES.setCallbackData(PassengerActions.PASSENGERTYPE_LIST_OF_CITIES.name());

    }

    public static InlineKeyboardMarkup createSkipCommentKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(SKIP_COMMENT);
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
        return inlineKeyboardMarkup;
    }


    public static InlineKeyboardMarkup createOrderConfirmKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(CONFIRM, DECLINE);
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createButtonForGetListOfCities(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(GET_LIST_OF_CITIES);
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
        return inlineKeyboardMarkup;
    }

}


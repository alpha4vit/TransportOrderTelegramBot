package by.gurinovich.PricePodschet.models.buttons;

import by.gurinovich.PricePodschet.utils.OrderType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class GeneralActionButtons {
    private static final InlineKeyboardButton PASSENGER_BUTTON = new InlineKeyboardButton("Стать пассажиром!");
    private static final InlineKeyboardButton CARGO_BUTTON = new InlineKeyboardButton("Передать посылку!");
    private static final InlineKeyboardButton INDIVIDUAL_BUTTON = new InlineKeyboardButton("Индивидуальный переезд!");
    private static final InlineKeyboardButton CREATE_NEW_ORDER = new InlineKeyboardButton("Сделать новый заказ!");

    static {
        CARGO_BUTTON.setCallbackData(OrderType.CARGO_ORDER.name());
        PASSENGER_BUTTON.setCallbackData(OrderType.PASSENGER_ORDER.name());
        INDIVIDUAL_BUTTON.setCallbackData(OrderType.INDIVIDUAL_ORDER.name());
        CREATE_NEW_ORDER.setCallbackData(OrderType.NEW_ORDER.name());

    }

    public static InlineKeyboardMarkup actionChooseButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(CARGO_BUTTON, PASSENGER_BUTTON, INDIVIDUAL_BUTTON);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        buttons.forEach(el -> keyboard.add(List.of(el)));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createNewOrderButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(CREATE_NEW_ORDER);
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
        return inlineKeyboardMarkup;
    }

}

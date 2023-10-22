package by.gurinovich.PricePodschet.models.buttons;

import by.gurinovich.PricePodschet.utils.individual.IndividualActions;
import by.gurinovich.PricePodschet.utils.passengers.PassengerActions;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class IndividualButtons {
    private static final InlineKeyboardButton CONFIRM = new InlineKeyboardButton("Подтвердить!");
    private static final InlineKeyboardButton DECLINE = new InlineKeyboardButton("Отменить!");

    private static final InlineKeyboardButton APPLY_OWNER = new InlineKeyboardButton("Да!");
    private static final InlineKeyboardButton DECLINE_OWNER = new InlineKeyboardButton("Нет!");


    private static final InlineKeyboardButton SKIP_COMMENT = new InlineKeyboardButton("Пропустить!");

    static {
        CONFIRM.setCallbackData(IndividualActions.INDIVIDUALTYPE_CONFIRMATION.name());
        DECLINE.setCallbackData(IndividualActions.INDIVIDUALTYPE_DECLINE.name());
        SKIP_COMMENT.setCallbackData(IndividualActions.INDIVIDUALTYPE_SKIP_COMMENT.name());
        APPLY_OWNER.setCallbackData(IndividualActions.INDIVIDUALTYPE_APPLY_OWNER.name());
        DECLINE_OWNER.setCallbackData(IndividualActions.INDIVIDUALTYPE_DECLINE_OWNER.name());
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

    public static InlineKeyboardMarkup createConfirmOwnerKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(APPLY_OWNER, DECLINE_OWNER);
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
        return inlineKeyboardMarkup;
    }


}

package by.gurinovich.PricePodschet.models.buttons;


import by.gurinovich.PricePodschet.utils.cargo.CargoActions;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CargoButtons {
    private static final InlineKeyboardButton BOX_S = new InlineKeyboardButton("Коробка S");
    private static final InlineKeyboardButton BOX_M = new InlineKeyboardButton("Коробка M");
    private static final InlineKeyboardButton BOX_L = new InlineKeyboardButton("Коробка L");
    private static final InlineKeyboardButton BOX_XL = new InlineKeyboardButton("Коробка XL");
    private static final InlineKeyboardButton DOCUMENTS = new InlineKeyboardButton("Документы");
    private static final InlineKeyboardButton ANOTHER_VARIANT = new InlineKeyboardButton("Cвой вариант");

    private static final InlineKeyboardButton CONFIRM = new InlineKeyboardButton("Подтвердить!");
    private static final InlineKeyboardButton DECLINE = new InlineKeyboardButton("Отменить!");

    private static final InlineKeyboardButton SKIP_COMMENT = new InlineKeyboardButton("Пропустить!");

    private static final InlineKeyboardButton GET_LIST_OF_CITIES = new InlineKeyboardButton("Получить список доступных городов!");

    static {
        BOX_S.setCallbackData(CargoActions.CARGOTYPE_BOX_S.name());
        BOX_M.setCallbackData(CargoActions.CARGOTYPE_BOX_M.name());
        BOX_L.setCallbackData(CargoActions.CARGOTYPE_BOX_L.name());
        BOX_XL.setCallbackData(CargoActions.CARGOTYPE_BOX_XL.name());
        DOCUMENTS.setCallbackData(CargoActions.CARGOTYPE_DOCUMENT.name());
        ANOTHER_VARIANT.setCallbackData(CargoActions.CARGOTYPE_ANOTHER.name());

        CONFIRM.setCallbackData(CargoActions.CARGOTYPE_CONFIRMATION.name());
        DECLINE.setCallbackData(CargoActions.CARGOTYPE_DECLINE.name());

        SKIP_COMMENT.setCallbackData(CargoActions.CARGOTYPE_SKIP_COMMENT.name());

        GET_LIST_OF_CITIES.setCallbackData(CargoActions.CARGOTYPE_LIST_OF_CITIES.name());

    }

    public static InlineKeyboardMarkup createChooseTypeKeyboard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(BOX_S, BOX_M, BOX_L, BOX_XL, DOCUMENTS, ANOTHER_VARIANT);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (int i =0; i < buttons.size()-1; i+=2)
            keyboard.add(List.of(buttons.get(i), buttons.get(i+1)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createOrderConfirmKeyboard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(CONFIRM, DECLINE);
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createSkipCommentKeyboard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = List.of(SKIP_COMMENT);
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

package by.gurinovich.PricePodschet.models.buttons;


import by.gurinovich.PricePodschet.utils.cargo.CargoTypes;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
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

    static {
        BOX_S.setCallbackData(CargoTypes.CARGOTYPE_BOX_S.name());
        BOX_M.setCallbackData(CargoTypes.CARGOTYPE_BOX_M.name());
        BOX_L.setCallbackData(CargoTypes.CARGOTYPE_BOX_L.name());
        BOX_XL.setCallbackData(CargoTypes.CARGOTYPE_BOX_XL.name());
        DOCUMENTS.setCallbackData(CargoTypes.CARGOTYPE_DOCUMENT.name());
        ANOTHER_VARIANT.setCallbackData(CargoTypes.CARGOTYPE_ANOTHER.name());

        CONFIRM.setCallbackData(CargoTypes.CARGOTYPE_CONFIRMATION.name());
        DECLINE.setCallbackData(CargoTypes.CARGOTYPE_DECLINE.name());
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


}

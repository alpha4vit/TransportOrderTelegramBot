package by.gurinovich.PricePodschet.services.sender;

import by.gurinovich.PricePodschet.config.BotConfig;
import by.gurinovich.PricePodschet.models.CargoOrder;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.services.orders.CargoOrderService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class DirectSender {
    private final BotConfig botConfig;
    private final UserService userService;
    private final CargoOrderService cargoOrderService;
    private final HTMLParser htmlParser;

    public SendMessage sendMessage(Long chatId){
        SendMessage message = new SendMessage();
        User recipient = userService.getByUsername(botConfig.getRecipient());
        CargoOrder cargoOrder = cargoOrderService.getUnsendCargoOrder(chatId);

        message.setChatId(recipient.getChatId());
        message.setText(String.format(htmlParser.readHTML("src/main/resources/static/messages/infoAboutOrder.html"),
                cargoOrder.getUser().getName(),
                cargoOrder.getValue(),
                cargoOrder.getDeparture(),
                cargoOrder.getDestination()));
        message.setParseMode("HTML");
        cargoOrderService.delete(cargoOrder);
        return message;
    }
}

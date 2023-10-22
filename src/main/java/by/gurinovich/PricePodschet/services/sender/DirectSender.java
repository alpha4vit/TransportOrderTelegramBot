package by.gurinovich.PricePodschet.services.sender;

import by.gurinovich.PricePodschet.config.BotConfig;
import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.services.orders.OrderService;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.parsers.HTMLParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DirectSender {
    private final BotConfig botConfig;
    private final UserService userService;
    private final OrderService orderService;
    private final HTMLParser htmlParser;

    public SendMessage sendMessage(Long chatId){
        SendMessage message = new SendMessage();
        User recipient = userService.getByUsername(botConfig.getRecipient());
        List<Order> orders = orderService.getUnsendOrders(chatId);
        StringBuilder messageText = new StringBuilder();
        orders.forEach(order ->
        {
            String username = order.getUser().getUsername() != null ? order.getUser().getUsername() : order.getUser().getName();
            switch (order.getType()){
                case CARGO_ORDER -> {
                    messageText.append(String.format(htmlParser.readHTML("src/main/resources/static/cargo/infoAboutOrder.html"),
                            username,
                            order.getType().name(),
                            order.getValue(),
                            order.getDeparture(),
                            order.getDestination(),
                            order.getComment()));
                }
                case PASSENGER_ORDER -> {
                    messageText.append(String.format(htmlParser.readHTML("src/main/resources/static/passenger/infoAboutOrder.html"),
                            username,
                            order.getType().name(),
                            order.getDeparture(),
                            order.getDestination(),
                            order.getComment()));
                }
                case INDIVIDUAL_ORDER -> {
                    messageText.append(String.format(htmlParser.readHTML("src/main/resources/static/individual/infoAboutOrder.html"),
                            username,
                            order.getType().name(),
                            order.isWithOwner() ? "Да" : "Нет",
                            order.getDeparture(),
                            order.getDestination(),
                            order.getComment()));
                }
            }
        });
        message.setChatId(recipient.getChatId());
        message.setText(messageText.toString());
        message.setParseMode("HTML");
        orders.forEach(orderService::delete);
        return message;
    }
}

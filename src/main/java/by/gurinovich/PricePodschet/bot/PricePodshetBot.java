package by.gurinovich.PricePodschet.bot;

import by.gurinovich.PricePodschet.config.BotConfig;
import by.gurinovich.PricePodschet.handlers.AnswerHandler;
import by.gurinovich.PricePodschet.services.sender.DirectSender;
import by.gurinovich.PricePodschet.services.users.UserService;
import by.gurinovich.PricePodschet.utils.states.BotState;
import by.gurinovich.PricePodschet.utils.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PricePodshetBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final AnswerHandler answerHandler;
    private final UserService userService;
    private final DirectSender directSender;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String messageText = null;
        Long chatId = null;
        String username= null;
        String name = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            username = update.getMessage().getFrom().getUserName();
            chatId = update.getMessage().getChatId();
            messageText = update.getMessage().getText();
            name = update.getMessage().getFrom().getFirstName();
            execute(answerHandler.answer(messageText, chatId, MessageType.MESSAGE, username, name));
        } else if (update.hasCallbackQuery()) {
            username = update.getCallbackQuery().getFrom().getUserName();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            messageText = update.getCallbackQuery().getData();
            name = update.getCallbackQuery().getFrom().getFirstName();
            execute(answerHandler.answer(messageText, chatId, MessageType.CALLBACK, username, name));
        }

        // send message about new order

        if (chatId != null){
            BotState botState = userService.getByChatId(chatId).getBotState();
            if (botState == BotState.SEND_MESSAGE){
                execute(directSender.sendMessage(chatId));
                userService.setState(chatId, BotState.START);
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

}

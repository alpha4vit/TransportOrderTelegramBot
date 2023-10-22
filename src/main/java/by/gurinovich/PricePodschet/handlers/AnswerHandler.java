package by.gurinovich.PricePodschet.handlers;


import by.gurinovich.PricePodschet.utils.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class AnswerHandler {

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    public SendMessage answer(String messageText, Long chatId, MessageType type, String username, String name) {
        SendMessage result = new SendMessage();
        switch (type){
            case MESSAGE -> result = messageHandler.answer(messageText, chatId, username, name);
            case CALLBACK -> result = callbackHandler.answer(messageText, chatId, username, name);
        }
        result.setChatId(chatId);
        return result;
    }
}

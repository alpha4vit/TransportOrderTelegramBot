package by.gurinovich.PricePodschet.services.users;

import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.repositories.OrderRepository;
import by.gurinovich.PricePodschet.repositories.UserRepository;
import by.gurinovich.PricePodschet.utils.states.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public User getByChatId(Long chatId){
        return userRepository.findByChatId(chatId).orElse(null);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public User save(Long chatId, String username,String name){
        if (userRepository.findByChatId(chatId).isPresent()){
            System.out.println("User with this chatId already exists");
            return null;
        }
        User user = User.builder()
                .chatId(chatId)
                .username(username)
                .name(name)
                .botState(BotState.START)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public void setState(Long chatId, BotState state){
        User user = getByChatId(chatId);
        user.setBotState(state);
        userRepository.save(user);
    }

    @Transactional
    public void clearOrders(Long chatId){
        User user = getByChatId(chatId);
        for (Order order : user.getOrders()) {
            if (!order.isConfirmed())
                orderRepository.delete(order);
        }
    }
}

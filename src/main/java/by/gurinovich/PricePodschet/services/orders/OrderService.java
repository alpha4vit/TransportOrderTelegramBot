package by.gurinovich.PricePodschet.services.orders;

import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.repositories.OrderRepository;
import by.gurinovich.PricePodschet.repositories.UserRepository;
import by.gurinovich.PricePodschet.utils.OrderType;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public void setWithOwner(Long chatId, OrderType orderType){
        Order order = getUnconfirmedOrder(chatId, orderType);
        order.setWithOwner(true);
        orderRepository.save(order);
    }

    @Transactional
    public void delete(Order order){
        orderRepository.delete(order);
    }

    public List<Order> getUnsendOrders(Long chatId){
        User user = userRepository.findByChatId(chatId).orElse(null);
        return orderRepository.findByUserAndSendedAndConfirmed(user, false, true);
    }

    public Order getUnconfirmedOrder(Long chatId, OrderType type){
        User user = userRepository.findByChatId(chatId).orElse(null);
        Optional<Order> cargoOrder = orderRepository.findByUserAndConfirmedAndType(user, false, type);
        return cargoOrder.orElseGet(() -> createUnfinished(chatId, type));
    }

    @Transactional
    public Order createUnfinished(Long chatId, OrderType type){
        return orderRepository.save(Order.builder().type(type).user(userRepository.findByChatId(chatId).orElse(null)).build());
    }

    @Transactional
    public void setComment(Long chatId, String comment, OrderType type){
        Order order = getUnconfirmedOrder(chatId, type);
        order.setComment(comment);
        orderRepository.save(order);
    }

    @Transactional
    public void setValue(Long chatId, String value, OrderType type){
        Order order = getUnconfirmedOrder(chatId, type);
        order.setValue(value);
        orderRepository.save(order);
    }

    @Transactional
    public void setRoute(Long chatId, String departure, String destination, OrderType type){
        Order order = getUnconfirmedOrder(chatId, type);
        order.setDeparture(departure);
        order.setDestination(destination);
        orderRepository.save(order);
    }

    @Transactional
    public void confirm(Long chatId, OrderType type){
        Order order = getUnconfirmedOrder(chatId, type);
        order.setConfirmed(true);
        orderRepository.save(order);
    }

    @Transactional
    public void deleteUncofirmedByChatIdAndType(Long chatId, OrderType type){
        User user = userRepository.findByChatId(chatId).orElse(null);
        orderRepository.deleteByUserAndConfirmedAndType(user, false, type);
    }
}

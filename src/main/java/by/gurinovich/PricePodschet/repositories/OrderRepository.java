package by.gurinovich.PricePodschet.repositories;

import by.gurinovich.PricePodschet.models.Order;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.utils.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndConfirmedAndType(User user, boolean confirmed, OrderType type);
    List<Order> findByUserAndSendedAndConfirmed(User user, boolean sended, boolean confirmed);
    void deleteByUserAndConfirmedAndType(User user, boolean confirmed, OrderType type);
}

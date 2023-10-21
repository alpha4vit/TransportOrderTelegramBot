package by.gurinovich.PricePodschet.repositories;

import by.gurinovich.PricePodschet.models.CargoOrder;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.utils.states.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoOrderRepository extends JpaRepository<CargoOrder, Long> {
    Optional<CargoOrder> findByUserAndConfirmed(User user, boolean confirmed);
    Optional<CargoOrder> findByUserAndSended(User user, boolean sended);
    void deleteByUserAndConfirmed(User user, boolean confirmed);
}

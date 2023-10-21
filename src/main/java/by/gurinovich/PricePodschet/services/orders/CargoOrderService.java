package by.gurinovich.PricePodschet.services.orders;

import by.gurinovich.PricePodschet.models.CargoOrder;
import by.gurinovich.PricePodschet.models.User;
import by.gurinovich.PricePodschet.repositories.CargoOrderRepository;
import by.gurinovich.PricePodschet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CargoOrderService {
    private final CargoOrderRepository cargoOrderRepository;
    private final UserRepository userRepository;

    @Transactional
    public void delete(CargoOrder cargoOrder){
        cargoOrderRepository.delete(cargoOrder);
    }

    public CargoOrder getUnsendCargoOrder(Long chatId){
        User user = userRepository.findByChatId(chatId).orElse(null);
        Optional<CargoOrder> cargoOrder = cargoOrderRepository.findByUserAndSended(user, false);
        return cargoOrder.orElse(null);
    }

    public CargoOrder getUnconfirmedCargoOrder(Long chatId){
        User user = userRepository.findByChatId(chatId).orElse(null);
         Optional<CargoOrder> cargoOrder = cargoOrderRepository.findByUserAndConfirmed(user, false);
        return cargoOrder.orElseGet(() -> createUnfinished(chatId));
    }

    @Transactional
    public CargoOrder createUnfinished(Long chatId){
        return cargoOrderRepository.save(CargoOrder.builder().user(userRepository.findByChatId(chatId).orElse(null)).build());
    }

    @Transactional
    public void setValue(Long chatId, String value){
        CargoOrder cargoOrder = getUnconfirmedCargoOrder(chatId);
        cargoOrder.setValue(value);
        cargoOrderRepository.save(cargoOrder);
    }

    @Transactional
    public void setRoute(Long chatId, String departure, String destination){
        CargoOrder cargoOrder = getUnconfirmedCargoOrder(chatId);
        cargoOrder.setDeparture(departure);
        cargoOrder.setDestination(destination);
        cargoOrderRepository.save(cargoOrder);
    }

    @Transactional
    public void confirm(Long chatId){
        CargoOrder cargoOrder = getUnconfirmedCargoOrder(chatId);
        cargoOrder.setConfirmed(true);
        cargoOrderRepository.save(cargoOrder);
    }

    @Transactional
    public void deleteUncofirmedByChatId(Long chatId){
        User user = userRepository.findByChatId(chatId).orElse(null);
        cargoOrderRepository.deleteByUserAndConfirmed(user, false);
    }
}

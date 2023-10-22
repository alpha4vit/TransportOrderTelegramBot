package by.gurinovich.PricePodschet.services.orders;

import by.gurinovich.PricePodschet.utils.OrderType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
@RequiredArgsConstructor
public class CityService {

    private final OrderService orderService;

    private final Set<String> cities = new HashSet<>(List.of("минск", "гродно", "белосток", "варшава", "вроцлав", "гданьск", "познань", "лодзь", "несвиж"));


    public boolean setRouteToOrder(Long chatId, String route, OrderType type){
        String[] parsed = route.split("-");
        if (route.matches("[a-zA-Z]+-[a-zA-Z]+")
         || !cities.contains(parsed[0].toLowerCase())
            || !cities.contains(parsed[1].toLowerCase())){
            return false;
        }
        orderService.setRoute(chatId, parsed[0], parsed[1], type);
        return true;
    }
}

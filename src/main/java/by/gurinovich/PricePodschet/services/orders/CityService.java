package by.gurinovich.PricePodschet.services.orders;

import by.gurinovich.PricePodschet.exceptions.InvalidOperationException;
import by.gurinovich.PricePodschet.exceptions.ResourceNotFoundException;
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

    private final CargoOrderService cargoOrderService;

    private final Set<String> cities = new HashSet<>(List.of("минск", "гродно", "белосток", "варшава", "вроцлав", "гданьск", "познань", "лодзь"));


    public boolean setRouteToOrder(Long chatId, String route){
        String[] parsed = route.split("-");
        if (route.matches("[a-zA-Z]+-[a-zA-Z]+")
         || !cities.contains(parsed[0].toLowerCase())
            || !cities.contains(parsed[1].toLowerCase())){
            return false;
        }
        cargoOrderService.setRoute(chatId, parsed[0], parsed[1]);
        return true;
    }
}

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

    private final Set<String> cities = new HashSet<>(List.of("Минск", "Гродно", "Белосток", "Варшава", "Вроцлав", "Гданьск", "Познань", "Лодзь", "Несвиж", "Краков"));

    public boolean setRouteToOrder(Long chatId, String route, OrderType type){
        String[] parsed = route.split("-");
        if (route.matches("[a-zA-Z]+-[a-zA-Z]+")
         || cities.stream().noneMatch(el -> el.equalsIgnoreCase(parsed[0]))
            || cities.stream().noneMatch(el -> el.equalsIgnoreCase(parsed[1]))){
            return false;
        }
        orderService.setRoute(chatId, parsed[0], parsed[1], type);
        return true;
    }

    public String getStringOfAvailableCities(){
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (String el : cities.stream().sorted().toList()){
            result.append(el);
            if (index != cities.size())
                result.append(", ");
            else
                result.append(" ");
            if (index %2 == 0)
                result.append("\n");
            ++index;
        }
        return result.toString();
    }
}

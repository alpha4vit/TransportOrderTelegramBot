package by.gurinovich.PricePodschet.models;


import by.gurinovich.PricePodschet.utils.states.BotState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "bot_state")
    private BotState botState;

    @OneToMany(mappedBy = "user")
    List<CargoOrder> cargoOrders;

}

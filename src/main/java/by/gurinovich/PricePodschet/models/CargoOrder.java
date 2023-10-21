package by.gurinovich.PricePodschet.models;

import by.gurinovich.PricePodschet.utils.states.OrderState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "cargo_orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private User user;

    @Column(name = "value")
    private String value;

    @Column(name = "departure")
    private String departure;

    @Column(name = "destination")
    private String destination;


    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "sended")
    private boolean sended;

}

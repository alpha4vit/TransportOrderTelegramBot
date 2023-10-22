package by.gurinovich.PricePodschet.models;

import by.gurinovich.PricePodschet.utils.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private User user;

    @Column(name = "value")
    private String value;

    @Column(name = "comment")
    private String comment;

    @Column(name = "departure")
    private String departure;

    @Column(name = "destination")
    private String destination;


    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "sended")
    private boolean sended;

    @Column(name = "with_owner")
    private boolean withOwner;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

}

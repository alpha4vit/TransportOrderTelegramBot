package by.gurinovich.PricePodschet.utils.states;

public enum BotState {
    START, CARGO_ORDER, CARGO_ORDER_ANOTHER,
    PASSENGER_ORDER, INDIVIDUAL_ORDER, CARGO_ORDER_ROUTE, PASSENGER_ORDER_ROUTE,
    INDIVIDUAL_ORDER_ROUTE, CARGO_ORDER_COMMENT, PASSENGER_ORDER_COMMENT,
    CONFIRMATION, SEND_MESSAGE, INDIVIDUAL_OWNER_CHECK, INDIVIDUAL_OWNER_COMMENT
}

package by.gurinovich.PricePodschet.utils;

public enum OrderType {
    CARGO_ORDER("Посылка"), PASSENGER_ORDER("Пассажир"), INDIVIDUAL_ORDER("Индивидуальный переезд"), NEW_ORDER("Новый заказ");


    OrderType(String name) {
    }
}

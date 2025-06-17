package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("=== Все перелёты ===");
        flights.forEach(System.out::println);

        System.out.println("=== Перелёты без некорректных сегментов ===");
        FlightFilterService.filterFlights(flights,
                        new FlightFilters(null, null, 0))
                .forEach(System.out::println);

        System.out.println("=== Перелёты без вылетов в прошлом ===");
        FlightFilterService.filterFlights(flights,
                        new FlightFilters(LocalDateTime.now(), null, 0))
                .forEach(System.out::println);

        System.out.println("=== Перелёты без длинных пересадок ===");
        FlightFilterService.filterFlights(flights,
                        new FlightFilters(null, null, 2))
                .forEach(System.out::println);
    }
}
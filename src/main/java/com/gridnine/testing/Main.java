package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("=== Все перелёты ===");
        flights.forEach(System.out::println);

        System.out.println("=== Перелёты без некорректных сегментов ===");
        FlightFilterService.removeInvalidSegments(flights).stream()
                .forEach(System.out::println);

        System.out.println("=== Перелёты без вылетов в прошлом ===");
        FlightFilterService.removeDepartedFlights(flights).stream()
                .forEach(System.out::println);

        System.out.println("=== Перелёты без длинных пересадок ===");
        FlightFilterService.removeFlightsWithLongTransfers(flights).stream()
                .forEach(System.out::println);
    }
}
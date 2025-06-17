package com.gridnine.testing;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class FlightFilterService {

    public static List<Flight> filterFlights(List<Flight> flights, FlightFilters filters) {
        return flights.stream()
                .filter(flight -> filters.departureBefore() == null && filters.arrivalBefore() == null ||
                        isNotDepartedOrArrived(flight, filters))
                .filter(flight -> filters.maxTransferHours() == 0 || hasValidTransfers(flight, filters.maxTransferHours()))
                .filter(flight -> isValidSegments(flight))
                .toList();
    }

    private static boolean isValidSegments(Flight flight) {
        return flight.getSegments().stream()
                .noneMatch(s -> s.getArrivalDate().isBefore(s.getDepartureDate()));
    }

    private static boolean isNotDepartedOrArrived(Flight flight, FlightFilters filters) {
        return flight.getSegments().stream()
                .noneMatch(s ->
                        (filters.departureBefore() != null &&
                                s.getDepartureDate().isBefore(filters.departureBefore())) ||
                                (filters.arrivalBefore() != null &&
                                        s.getArrivalDate().isBefore(filters.arrivalBefore()))
                );
    }

    private static boolean hasValidTransfers(Flight flight, int maxHours) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() <= 1) return true;

        for (int i = 0; i < segments.size() - 1; i++) {
            long hours = ChronoUnit.HOURS.between(
                    segments.get(i).getArrivalDate(),
                    segments.get(i + 1).getDepartureDate());
            if (hours > maxHours) return false;
        }
        return true;
    }
}
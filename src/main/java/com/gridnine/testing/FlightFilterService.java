package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterService {

    public static List<Flight> removeInvalidSegments(List<Flight> flights) {
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flights) {
            boolean isValid = true;
            for (Segment segment : flight.getSegments()) {
                if (!segment.getArrivalDate().isAfter(segment.getDepartureDate())) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                validFlights.add(flight);
            }
        }
        return validFlights;
    }

    public static List<Flight> removeDepartedFlights(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flights) {
            boolean allValid = true;
            for (Segment segment : flight.getSegments()) {
                if (segment.getDepartureDate().isBefore(now)) {
                    allValid = false;
                    break;
                }
            }
            if (allValid) {
                validFlights.add(flight);
            }
        }
        return validFlights;
    }

    public static List<Flight> removeFlightsWithLongTransfers(List<Flight> flights) {
        final int MAX_TOTAL_TRANSFER_HOURS = 2;
        List<Flight> validFlights = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            long totalTransferTime = 0;

            if (segments.size() > 1) {
                for (int i = 0; i < segments.size() - 1; i++) {
                    totalTransferTime += ChronoUnit.HOURS.between(
                            segments.get(i).getArrivalDate(),
                            segments.get(i + 1).getDepartureDate()
                    );
                }
            }

            if (totalTransferTime <= MAX_TOTAL_TRANSFER_HOURS) {
                validFlights.add(flight);
            }
        }
        return validFlights;
    }
}
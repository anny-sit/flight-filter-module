package com.gridnine.testing;

import java.time.LocalDateTime;

public record FlightFilters(
        LocalDateTime departureBefore,
        LocalDateTime arrivalBefore,
        int maxTransferHours
) {}
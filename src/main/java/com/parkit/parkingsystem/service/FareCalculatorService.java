package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        // Validate the out time
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect: " + ticket.getOutTime());
        }

        // Calculate duration in minutes
        long inMinutes = ticket.getInTime().getTime() / 1000 / 60;
        long outMinutes = ticket.getOutTime().getTime() / 1000 / 60;
        long durationMinutes = outMinutes - inMinutes;

        // Determine fare based on duration
        double price = 0.0;

        if (durationMinutes < 15) {
            price = 0.0; // No charge for parking less than 15 minutes
        } else {
            double durationHours = durationMinutes / 60.0; // Calculate duration in fractional hours
            double rate;

            // Set rate based on parking type
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR:
                    rate = Fare.CAR_RATE_PER_HOUR;
                    price = durationHours * rate; // Calculate price based on car rate
                    break;
                case BIKE:
                    rate = Fare.BIKE_RATE_PER_HOUR;
                    price = durationHours * rate; // Calculate price based on bike rate
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Parking Type");
            }
        }

        // Set the calculated price in the ticket
        ticket.setPrice(price);
    }
}

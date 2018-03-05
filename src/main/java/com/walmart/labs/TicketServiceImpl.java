package com.walmart.labs;

public class TicketServiceImpl implements TicketService {

    /**
     *
     * @return return the available seats from the singleton instance as we keep track
     * of the value when holding and reserving seats.
     */
    public int numSeatsAvailable() {
        return Venue.getInstance().getAvailableSeats();
    }

    /**
     *
     * @param numSeats the number of seats to find and hold
     * @param customerEmail customer email address
     * @return null when requested seats > available seats
     * or return/generate seatHoldId and hold it for configured time
     */
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        if(numSeats>Venue.getInstance().getAvailableSeats())
        {
            System.out.print("The no of seats requested is greater than available seats!");
            return null;
        }
        SeatHold seatHold =new SeatHold(customerEmail,-1);
        return seatHold.holdSeats(numSeats);
    }

    /**
     *
     * @param seatHoldId the seat hold identifier
     * @param customerEmail the email address of the customer to which the
     * seat hold is assigned
     * @return random UUID as the confirmation String. The program does not record it.
     */
    public String reserveSeats(int seatHoldId, String customerEmail) {

        return new SeatHold(customerEmail,seatHoldId).ReserveSeats();
    }
}

package com.walmart.labs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketServiceImplTest {

    Venue venue;

    TicketService ticketService = new TicketServiceImpl();

    String email ="test@email.com";

    @Before
    public void init()
    {
        venue=Venue.getInstance();
        venue.setSeatHoldTime(10000);
        venue.setSeatHoldCounter(0);
        venue.setVenueMatrix(new Boolean[9][9]);

    }

    @Test
    public void numSeatsAvailable() {
        System.out.println("No of Seats:"+ticketService.numSeatsAvailable());
        assertEquals (ticketService.numSeatsAvailable(),81);
    }

    @Test
    public void findAndHoldSeats() {
        SeatHold s=ticketService.findAndHoldSeats(4,email);
        System.out.println("Seat hold id:"+s.getSeatHoldId());
        assertEquals(s.getSeatHoldId(),1);
        assertEquals(ticketService.numSeatsAvailable(),81-4);

    }

    @Test
    public void findAndHoldLargeSeats() {
        SeatHold s=ticketService.findAndHoldSeats(400,email);
        assertNull(s);
    }

    @Test
    public void reserveSeats() {
        findAndHoldSeats();
        String uuid= ticketService.reserveSeats(1,email);
        System.out.println("GUID:"+uuid);
        assertNotSame (uuid,null);

    }

    @Test
    public void reserveInValidCustomerEmail()
    {
        findAndHoldSeats();
        String uuid= ticketService.reserveSeats(1,email+"test");
        System.out.println("GUID:"+uuid);
        assertNull (null,null);

    }

    @Test
    public void reserveInValidHoldID()
    {
        findAndHoldSeats();
        String uuid= ticketService.reserveSeats(99,email+"test");
        System.out.println("GUID:"+uuid);
        assertNull (null,null);

    }





}
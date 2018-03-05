package com.walmart.labs;

import org.junit.Assert;
import org.junit.Test;

import static java.lang.Thread.*;
import static org.junit.Assert.*;


public class SeatHoldTest {

    String email = "test@email.com";
    private Venue venue;


    public void init()
    {
        venue=Venue.getInstance();
        venue.setSeatHoldCounter(0);
    }


    @Test
    public void testSeatHoldConstructor()
    {
        init();
        int seatHoldId=-1;
        int seatHoldId1=1;
        String customerEmail=email;
        SeatHold seatHold=new SeatHold(customerEmail,seatHoldId);
        SeatHold seatHold1= new SeatHold(customerEmail,seatHoldId1);
        assertEquals(seatHold,seatHold1);

    }

    @Test
    public void  testSeatHoldEqualsAndHashCode() {
        init();
        int seatHoldId=-1;
        int seatHoldId1=1;
        String customerEmail="test@email.com";
        SeatHold seatHold=new SeatHold(customerEmail,seatHoldId);
        SeatHold seatHold1= new SeatHold(customerEmail,seatHoldId1);

        Assert.assertTrue(seatHold.equals(seatHold1));
        Assert.assertTrue(seatHold.hashCode()==seatHold1.hashCode());
    }

    @Test
    public void holdSeatsOneTime() {
        init();
        int row=9;
        int column=9;
        venue.setVenueMatrix(new Boolean[row][column]);
        System.out.println("Available Seats:"+venue.getAvailableSeats());
        venue.setSeatHoldTime(10);

        SeatHold s = new SeatHold(email,-1);
        s.holdSeats(9);
        assertEquals(venue.getAvailableSeats(),72);

    }

    @Test
    public void testReserveSeatsOneTime() throws InterruptedException {
        holdSeatsOneTime();
        SeatHold s =new SeatHold(email,1);
        s.ReserveSeats();
        sleep(100);
        assertEquals(venue.getAvailableSeats(),72);

    }


    @Test
    public void holdSeatsNTimeFor30MiliSeconds() {
        init();
        int n=9;
        venue=Venue.getInstance();
        venue.setVenueMatrix(new Boolean[n][n]);
        venue.setSeatHoldTime(100);
        for (int i=0;i<9;i++)
        {
            SeatHold s = new SeatHold(email,-1);
            s.holdSeats(9);
        }
        assertEquals(venue.getAvailableSeats(),0);

    }

    @Test
    public void testRerveSeatsNTimeFor30MiliSeconds() throws InterruptedException {

        holdSeatsNTimeFor30MiliSeconds();
        for(int i=1;i<=3;i++)
        {
            SeatHold s =new SeatHold(email,i);
            s.ReserveSeats();
        }
        sleep(2000);
        assertEquals(venue.getAvailableSeats(),54);
    }


}
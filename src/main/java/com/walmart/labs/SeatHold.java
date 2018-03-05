package com.walmart.labs;

import java.util.*;

public class SeatHold
{

    private String customerEmail ;

    private int seatHoldId;

    //The singleton object to access common properties of the venue
    private Venue venue;


    /**
     *Constructor to generate the seat hold and and initialize
     * @param customerEmail customer email
     * @param seatHoldId seatHoldID
     */
    public SeatHold(String customerEmail,int seatHoldId) {

        venue=Venue.getInstance();

        this.customerEmail=customerEmail;
        if(seatHoldId==-1)
        {
            this.seatHoldId=venue.getNextSeatHoldCounter();
        }
        else {
            this.seatHoldId=seatHoldId;
        }
    }

    /**
     * Hold seats for against the customer information
     * @param numSeats number of seats to hold
     * @return the instance of the same object
     */
    public SeatHold holdSeats(int numSeats)
    {
        blockSeats(numSeats);

        Thread seatHoldT=new Thread(new Runnable(){
            public void run() {
                    try {
                        Thread.sleep(venue.getSeatHoldTime());

                        //release the seats after configured second
                        releaseOrReserveSeats(Operation.RELEASE);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        seatHoldT.start();
      //  System.out.println(seatHoldId);
        return this;
    }

    /**
     * Reserve Seats
     * @return confirmation code only if the customer information matches
     */
    public String ReserveSeats()
    {
        if(!releaseOrReserveSeats(Operation.RESERVE))
        {
            return null;
        }
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param operation a seat hold or seat reserve
     * true -if reservation is made
     * false - if reservation is not made ( doesn't match seatHoldId and customer email)
     */
    private boolean releaseOrReserveSeats(Operation operation)
    {
        //hold and reserve should be stream lined as various thread
        //accesses the singleton objects
        synchronized (venue.getLockObject())
        {
            if(venue.getVenueHoldedSeats().containsKey(this))
            {
                //get the seat information for this hold,iterate and do operation
                //specfic task.
                Map<Integer, List<Integer>> customerSeats=venue.getVenueHoldedSeats().get(this);
                Iterator<Map.Entry<Integer, List<Integer>>> it = customerSeats.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, List<Integer>> entry = it.next();
                    List<Integer> columns = entry.getValue();
                    int r =  entry.getKey();
                    for (int c : columns) {
                        if( operation.equals(Operation.RELEASE))
                        {
                            //release the seats and increase the available seats
                            venue.getVenueMatrix()[r][c] = null;
                            venue.incrementAvailableSeats();
                        }
                        if(operation.equals(Operation.RESERVE))
                        {
                            // reserve the seat.
                            venue.getVenueMatrix()[r][c] = true;
                        }

                    }
                    it.remove();
                }
                venue.getVenueHoldedSeats().remove(this);
            }
            else if (operation==Operation.RESERVE){
                System.out.println("The holdId and customer email doesn't hold any seats. Try again!");
                return false;
            }

        }
        return true;

    }

    /**
     * Private method to block/hold the seats
     * @param numSeats no of seats to hold
     */
    private void blockSeats(int numSeats)
    {
            int counter = 0;
            //keep track of the seats(columns) that we are holding for each row
            Map<Integer, List<Integer>> seats = new HashMap<>();

            for (int i = 0; i < venue.getVenueMatrix().length; i++) {

                List<Integer> columnSeats = new ArrayList<>();
                for (int j = 0; j < venue.getVenueMatrix()[i].length; j++) {
                    if ((venue.getVenueMatrix()[i][j] == null) && counter < numSeats) {
                        venue.getVenueMatrix()[i][j] = false;
                        counter++;
                        columnSeats.add(j);
                        venue.decrementAvailableSeats();
                    }
                }
                seats.put(i, columnSeats);
                //exit once we reached the number of seats.
                if (counter >= numSeats) {
                    break;
                }
            }
            // put the row/column map collected above in to the master seat hold map(in singleton instance)
            venue.getVenueHoldedSeats().put(this, seats);

    }


    /**
     *
     * @param o an input object
     * @return if the obeject is equal
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        /*

         Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false*/
        if (!(o instanceof SeatHold)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        SeatHold s = (SeatHold) o;

        // Compare the data members and return accordingly
        return seatHoldId==s.seatHoldId
                && customerEmail.equals(s.customerEmail);
    }

    /**
     * override the hasCode so we can compare/find the SeatHold java object
     * based on seatHoldId and customerEmail
     * @return new hash based on seatHoldId and customerEmail
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(seatHoldId,customerEmail);
    }


    public int getSeatHoldId() {
        return seatHoldId;
    }
}

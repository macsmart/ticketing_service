package com.walmart.labs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class to hold the Venue variables accessed by different customers
 */
public class Venue {

    private static Venue instance = null;

    //Venue Matrix nXn matrix
    private Boolean[][] venueMatrix;

    // keep track of the available seats
    private int availableSeats;

    // time used to hold the seats in milliseconds
    private int seatHoldTime;

    //counter to assign the seat hold id.
    private int seatHoldCounter;

    // object for locking code block on updating static objects in the thread
    private Object lockObject;

    // map of row and columns that a particular customer holds
    private HashMap<SeatHold,Map<Integer, List<Integer>>> venueHoldedSeats;


    private Venue()
    {
        venueMatrix=null;
        availableSeats=0;
        seatHoldCounter=0;
        venueHoldedSeats=new HashMap<SeatHold,Map<Integer, List<Integer>>>();
        lockObject=new Object();

    }

    public static Venue getInstance()
    {
        if (instance == null)
            instance = new Venue();

        return instance;
    }


    public Boolean[][] getVenueMatrix() {
        return venueMatrix;
    }

    public void setVenueMatrix(Boolean[][] venueMatrix) {
        this.venueMatrix = venueMatrix;
        this.availableSeats=venueMatrix.length*venueMatrix[0].length;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void incrementAvailableSeats()
    {
        this.availableSeats++;
    }

    public void decrementAvailableSeats()
    {
        this.availableSeats--;
    }


    public int getSeatHoldTime() {
        return seatHoldTime;
    }

    public void setSeatHoldTime(int seatHoldTime) {
        this.seatHoldTime = seatHoldTime;
    }

    public int getNextSeatHoldCounter() {
        return ++seatHoldCounter;
    }

    public void setSeatHoldCounter(int seatHoldCounter) {
        this.seatHoldCounter = seatHoldCounter;
    }

    public Object getLockObject() {
        return lockObject;
    }

    public void setLockObject(Object lockObject) {
        this.lockObject = lockObject;
    }

    public HashMap<SeatHold, Map<Integer, List<Integer>>> getVenueHoldedSeats() {
        return venueHoldedSeats;
    }

    public void setVenueHoldedSeats(HashMap<SeatHold, Map<Integer, List<Integer>>> venueHoldedSeats) {
        this.venueHoldedSeats = venueHoldedSeats;
    }
}

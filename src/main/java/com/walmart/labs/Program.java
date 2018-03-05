package com.walmart.labs;

import java.util.HashMap;

public class Program {



    public static void main(String[] args)
    {
        int holdId;
        String email;
        int numberofSeats;

        TicketService ticketService = new TicketServiceImpl();
        Venue venue=Venue.getInstance();

        //Get the input from the users about the no of rows and columns of the seats in the venue

        //rows of the stage
        System.out.println("Enter the no of seating rows in the venue for ticketing purpose:");
        int row = GetInput.getInputPositivInt("row");

        //column of the stage
        System.out.println("Enter the no of seating columns in the venue for ticketing purpose:");
        int column = GetInput.getInputPositivInt("column");

        // time to hold the seats for
        System.out.println("Enter the time in seconds to hold the seats for ticketing purpose:");
        int milliSec = GetInput.getInputPositivInt("time")*1000;

        venue.setVenueMatrix(new Boolean[row][column]);
        venue.setSeatHoldTime(milliSec);

        boolean performOperation= true;

        show();

        //loop to perform multiple operations
        while(performOperation)
        {
            System.out.println("Enter the operations you want to perform:");
            System.out.println("1. Type AVAILABLE for getting the no of available seats in the venue.");
            System.out.println("2. Type HOLD for finding and holding the best seats available on behalf of customer.");
            System.out.println("3. Type RESERVE for reserving and committing a specific group of held seats for a customer.");
            System.out.println("4. Type EXIT for exiting the program.");
            Operation op = GetInput.getOperation(GetInput.getString());

            try {

                switch (op) {
                    case EXIT:
                        performOperation = false;
                        System.out.println("Thanks for using ticketing service, Exiting...");
                        break;
                    case AVAILABLE:
                        System.out.println("*********************************************");
                        System.out.println("The no of seats available in the venue are:" + ticketService.numSeatsAvailable());
                        break;
                    case HOLD:
                        System.out.println("Enter the no of seats you would like to hold:");
                        numberofSeats = GetInput.getInputPositivInt("no of seats");
                        System.out.println("Enter the email id against which the hold should be placed:");
                        email = GetInput.getEmailString();
                        SeatHold seatHold = ticketService.findAndHoldSeats(numberofSeats, email);
                        System.out.println("*********************************************");
                        System.out.println(numberofSeats + " seats placed on hold. Your hold id is: " + seatHold.getSeatHoldId());
                        break;
                    case RESERVE:
                        System.out.println("Enter the hold id");
                        holdId = GetInput.getInputPositivInt("Hold Id");
                        System.out.println("Enter the email id that you used:");
                        email = GetInput.getEmailString();
                        String confirmationCode = ticketService.reserveSeats(holdId, email);
                        if (!confirmationCode.equals(null)) {
                            System.out.println("*********************************************");
                            System.out.println("You seats are reserved. Confirmation code :" + confirmationCode);
                        }
                        break;

                    case SHOW:
                        show();
                        break;
                    case INVALID:
                    default:
                        System.out.println("Please enter a valid operation..");

                }
                System.out.println("*********************************************");
            }
            catch (Exception e)
            {
                System.out.println("Exception occured while performing operations:"+e.getMessage());
            }

        }
    }


    public static void show()
    {
        Venue venue=Venue.getInstance();
        //Visual display of the stage
        System.out.println("*********************************************");
        System.out.println("*********************STAGE*******************");
        System.out.println("*********************************************");
        for(int i=0;i<venue.getVenueMatrix().length;i++)
        {
            for(int j=0;j<venue.getVenueMatrix()[i].length;j++)
            {
                if(venue.getVenueMatrix()[i][j]==null)
                {
                    System.out.print("A");
                }
                else {
                    System.out.print(!venue.getVenueMatrix()[i][j] ? "H" : "R");
                }
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("");
        System.out.println("A - indicates Available");
        System.out.println("H - indicates seats on Hold");
        System.out.println("R - indicates seats that are reserved");

    }

}


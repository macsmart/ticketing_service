package com.walmart.labs;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetInput {


    static Scanner reader = new Scanner(System.in);

    static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /**
     *
     * @param action
     * @return
     */
    public static int getInputPositivInt(String action)
    {

        int number;
        do{
            System.out.println("Input "+ action+":");
            while (!reader.hasNextInt())
            {
                System.out.println("Enter a positive number:");
                reader.next();
            }
            number= reader.nextInt();
        } while (number<0);
        return number;
    }

    /**
     *retuns the valid string from the console
     * @return
     */

    public static String getString()
    {
        return reader.next();
    }


    public static String getEmailString()
    {
        String email;

           do
            {
                System.out.println("Enter a valid email address:");
                email=reader.next();
            } while (!validateEmailAddress(email));

        return email;
    }


    /**
     *
     * @param emailStr
     * @return
     */
     public static boolean validateEmailAddress(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    /**
     *Method to convert in to Operation Enum, assign invalid if can not find an enum
     * @param input string to convert in to operation enum
     * @return
     */
    public static Operation getOperation(String input) {

        for (Operation o : Operation.values()) {
            if (o.name().equals(input)) {
                return o;
            }
        }

        return Operation.INVALID;
    }





}

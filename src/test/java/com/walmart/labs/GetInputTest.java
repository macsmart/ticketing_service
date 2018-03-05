package com.walmart.labs;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetInputTest {

    @Test
    public void testValidateEmailAddressHappyPath() {

        String email ="test@example.com";
        String email1="test1@ad1.com";
        assertTrue(GetInput.validateEmailAddress(email));
        assertTrue(GetInput.validateEmailAddress(email1));

    }

    @Test
    public void testValidateEmailAddressSadPath() {

        String email ="test@example.";
        String email1="test1@.com";
        assertFalse(GetInput.validateEmailAddress(email));
        assertFalse(GetInput.validateEmailAddress(email1));

    }


    @Test
    public void testGetOperationValid() {
        String o = "RESERVE";
        assertEquals(GetInput.getOperation(o),Operation.RESERVE);
        o="HOLD";
        assertEquals(GetInput.getOperation(o),Operation.HOLD);


    }

    @Test
    public void testGetOperationInValid() {
        String o = "MYOPERATIOn";
        assertEquals(GetInput.getOperation(o),Operation.INVALID);
    }
}
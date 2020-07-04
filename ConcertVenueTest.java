// Cache Money: Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434. Project 2, Iteration 1
// 4//2019
// ConcertTest.java
//
// This is the class to test the concert class

import static org.junit.Assert.*;
import org.junit.*;

public class ConcertTest
{
    private Concert testConcert1;
    private Concert testConcert2;
    
    @Test
    public void testGetName()
    {
        testConcert1 = new Concert(5, 5, "The Venue");
        testConcert2 = new Concert(6, 7, "The Concert");
        
        assertEquals("getName() returned the incorrect value", "The Venue", 
            testConcert1.getName());
        assertEquals("getName() returned the incorrect value", "The Concert", 
            testConcert2.getName());
    
    }

    @Test
    public void testGetSeatsAvailable()
    {
        testConcert1 = new Concert(3, 8, "The Venue");
        testConcert2 = new Concert(3, 5, "The Venue");
        assertEquals("The number of seats available is 15", 24,
            testConcert1.getSeatsAvailable());
        assertEquals("The number of seats available is 15", 15,
            testConcert2.getSeatsAvailable());
    }

    @Test
    public void testToString()
    {
        testConcert1 = new Concert(4, 5, "Venue");
        
        testConcert1.selectSeat("[01]");
        testConcert1.selectSeat("[20]");

        String testString1 = "[X][02][03][04][05]\n"
            + "[06][07][08][09][10]\n"
            + "[11][12][13][14][15]\n"
            + "[16][17][18][19][X]\n";

        assertEquals("Test venue 1 toString is not correct", testString1,
          testConcert1.toString());
    }
}

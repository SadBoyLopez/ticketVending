// Jesus Cruz, Albert Lopez, Jordyn Martin
// TestTicketBuyer
// This class is to test the TicketBuyer class

import static org.junit.Assert.*;
import org.junit.*;

public class TestTicketBuyer
{
    private TicketBuyer myBuyer;
    private String[] seats1;
    private String[] seats2;
    
    @Test
    public void testConstructor()
    { 
        myBuyer = new TicketBuyer("Cache");

        assertEquals("Name is Cache", "Cache", myBuyer.getName());
    }

    @Test
    public void testTicketCount()
    {
        myBuyer = new TicketBuyer("Cache");
        seats1 = new String[5];
        for (int i = 0; i < seats1.length; i++)
            seats1[i] = "[0" + (i+1) + "]";

        seats2 = new String[3];
        for (int i = 0; i < seats2.length; i++)
            seats2[i] = "[0" + (i+1) + "]";

        myBuyer.seatsPurchased("ASAP", seats1);
        myBuyer.seatsPurchased("Dinero", seats2);

        assertEquals("Ticket count is 8", 8, myBuyer.getTicketCount());
    }

    @Test
    public void testToString()
    {
        myBuyer = new TicketBuyer("Cache");

        seats2 = new String[3];
        for (int i = 0; i < seats2.length; i++)
            seats2[i] = "[0" + (i+1) + "]";

        myBuyer.seatsPurchased("Dinero", seats2);

        String testString = "Tickets bought so far: \n"
            + "Dinero : [[01], [02], [03]]\n";
        
        assertEquals("toString is incorrect", testString, myBuyer.toString());
    }
}

// Team Cache Money: Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 1
// 4/1/2019
// TicketBuyer.java
// 
// This is the class to represent a ticket buyer item.

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/** This is the class to represent a ticket buyer item.
 * @author Albert Lopez
 * @author Jesus Crus
 * @author Jordyn Martin
 */
public class TicketBuyer
{
    private String buyerName;
    private int numTicketsPurchased;
    private HashMap<Concert, ArrayList<String>> concertHashMap;

    /** Creates the ticket buyer
     * @param name takes the buyers name
     */
    public TicketBuyer(String name)
    {
        buyerName = name;
        numTicketsPurchased = 0;
        concertHashMap = new HashMap<Concert, ArrayList<String>>();
    }

    public String getName()
    {
        return buyerName;
    }
    /** Adds to the number of tickets the user wants to purchase.
     * @param tickets creates an integer for the users ticket amount.
     */
    public void ticketCount(int tickets)
    {
        numTicketsPurchased += tickets;
    }

    /** Returns the number of tickets the user has purchased.
     * @return Returns the number of tickets the user has bought
     */
    public int getTicketCount()
    {
        return numTicketsPurchased;
    }

    /** Checks the HashMap key for the venue and then adds the seat to the list
     * if the key matches.
     * @param venueName Creates the name for the venue.
     * @param seats Creates an array of seats for the user to choose from.
     */
    public void seatsPurchased(Concert currentConcert, String[] seats)
    {
        ArrayList<String> seatList;
        //Check key and get the arraylist for the concert venue value
        //If key is in map, add the seats to the list
        if (concertHashMap.containsKey(currentConcert))
        {
            seatList = concertHashMap.get(currentConcert);
            for (String seat : seats)
            {
                if (seat != null)
                    seatList.add(seat);
                    numTicketsPurchased++;
            }
            concertHashMap.put(currentConcert, seatList);
        }
        else
        {
            seatList = new ArrayList<String>();
            for (String seat : seats)
            {
                seatList.add(seat);
                numTicketsPurchased++;
            }
            concertHashMap.put(currentConcert, seatList);
            
        }
        //else key not set in map then create new array list
    }

    /** Creates the toString for the amount of tickets being bought.
     * @return Returns the number of tickets the user has bought.
     */
    public String toString()
    {
        String buyString = "Tickets bought so far: \n";
        Concert myConcert;
        if (concertHashMap.size() > 0)
        {
            for (Concert key : concertHashMap.keySet())
            {
                String seats = concertHashMap.get(key).toString().replace("[", "").replace("]", "");
                buyString += key.getName() + " : " + seats + "\n";
            }
        }
        else
        {
            buyString += "You have not bought any tickets yet!\n";
        }

        return buyString;
    }
    public String toStringComplete()
    {
        String buyString = "Tickets purchased: \n";
        Concert myConcert;
        if (concertHashMap.size() > 0)
        {
            for (Concert key : concertHashMap.keySet())
            {
                String seats = concertHashMap.get(key).toString().replace("[", "").replace("]", "");
                buyString += key.getName() + " : " + seats + "\n";
            }
        }
        return buyString;
    }
}

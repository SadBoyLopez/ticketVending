// Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 3
// 4/22/19
// TicketBoothSession.java
// 
// Session for the ticket booth client-server

import java.io.*;
import java.net.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Session for the ticket booth client-server
 * @author Albert J. Lopez Jr.
 * @author Jesus Cruz
 * @author Jordyn E. Martin
 */

public class TicketBoothSession implements TicketBoothConstants, Runnable
{
    private Socket myBuyer;
    private Concert currentConcert;
    private Concert[] concerts;

    /**
     * Constructor of TicketBoothSession class
     * @param myBuyer Establishes socket for the buyer
     */
    public TicketBoothSession(Socket myBuyer)
    {
        this.myBuyer = myBuyer;
    }

    /**
     * Run method to create the ticket booth
     */
    public void run()
    {
        try
        {
            DataInputStream fromBuyer = 
                new DataInputStream(myBuyer.getInputStream());
            DataOutputStream toBuyer = 
                new DataOutputStream(myBuyer.getOutputStream());
            

            while(true)
            {
                
                // Handle the section selection
                // gets the number of tickets
                // creates a count for available seats.

                int sectionChosen = fromBuyer.readInt();
                int numTicketsWanted = fromBuyer.readInt();               

                readFromSerFile();

                int seatsAssigned = 0;
                
                String seat;
                
                int[] wantedTicketsArr;
                String[] ticketsToPurchase = new String[numTicketsWanted];
               
                for (int i = 0; i < concerts.length; i++)
                {
                    currentConcert = concerts[i];
                }

                do
                {
                    
                    if (sectionChosen == SECTION_ONE)
                    {
                        if (currentConcert.getSeatsAvailable() < numTicketsWanted 
                            || currentConcert.isSoldOut() )
                        {   
                            toBuyer.writeInt(TICKETS_WANTED_UNAVAILABLE);
                        }
                        else
                        {   
                            // Check this soon to see if this is needed.
                            // toBuyer.writeObject(concerts);
                
                            wantedTicketsArr = new int[numTicketsWanted];
                                
                            for (int i = 0; i < wantedTicketsArr.length; i++)
                            {
                                if (wantedTicketsArr[i] <= 9)
                                {       
                                    seat = "[0" + (i + 1) + "]";
                                }
                                else
                                {
                                    seat = "[" + (i + 1) + "]";
                                }
                           
                        
                                if (!currentConcert.isSeatTaken(seat) 
                                    && seatsAssigned < numTicketsWanted)
                                {
                                    ticketsToPurchase[seatsAssigned] = seat;
                                    seatsAssigned++;
                                    toBuyer.writeUTF(ticketsToPurchase[i]);
                                }
                            }
                    

                        // finish writing to .ser fle
                        }
                    }
                    else if (sectionChosen == SECTION_TWO)
                    {
                    
                        if (currentConcert.getSeatsAvailable() < numTicketsWanted 
                            || currentConcert.isSoldOut() )
                        {
                            toBuyer.writeInt(TICKETS_WANTED_UNAVAILABLE);
                        }
                        else
                        {
                            for (int i = 20; i < 50; i++)
                            {
                                seat = "[" + (i + 1) + "]";
                            

                                if (!currentConcert.isSeatTaken(seat) 
                                    && seatsAssigned < numTicketsWanted)
                                {
                                    ticketsToPurchase[seatsAssigned] = seat;
                                    seatsAssigned++;
                                    toBuyer.writeUTF(ticketsToPurchase[i]);
                                }
                            }

                        }
                    }
                    else
                    {
                        for (int i = 50; i < currentConcert.getSize(); i++)
                        {
                            seat = "[" + (i + 1) + "]";

                            if (!currentConcert.isSeatTaken(seat) 
                                && seatsAssigned < numTicketsWanted)
                            {
                                ticketsToPurchase[seatsAssigned] = seat;
                                seatsAssigned++;
                                toBuyer.writeUTF(ticketsToPurchase[i]);
                            }
            
                        }
                    }
                

            
                    }while (fromBuyer.readInt() == NO);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    /**
     * Reads from the Ser file to obtain the number of seats available in a
     * concert.
     */
    private void readFromSerFile()
    {
        try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(FILENAME)))
        {
            Concert[] readConcertArr = (Concert[])
            inFile.readObject();

            concerts = new Concert[readConcertArr.length];
            // eventButtons = new JButton[readConcertArr.length];

            for (int i = 0; i < readConcertArr.length; i++)
            {
                concerts[i] = readConcertArr[i];
                // eventButtons[i] = new JButton(concerts[i].getName());
            }

        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could Not open "
                + FILENAME + " for reading.");
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}

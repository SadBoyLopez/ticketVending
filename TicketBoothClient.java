// Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 3 
// 4/22/19
// TicketBoothClient.java
//
// This is the client to the client-server relatuib for connect four

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Client for the ticket booth client-server
 * @author Albert J. Lopez Jr.
 * @author Jesus Cruz
 * @author Jordyn E. Martin
 */

public class TicketBoothClient extends Observable 
    implements TicketBoothConstants, Runnable
{
    private TicketBuyer myBuyer;
    
    private static final String SERVER = "localhost";

    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private boolean waiting;
    private boolean continueToPurchase;
    private int sectionsSelected;
    private int numTicketsWanted;

    /**
     * Starts thread for the ticket booth
     */
    public TicketBoothClient()
    {
        Thread myThread = new Thread(this);
        myThread.start();
    }

    public void run()
    {
        waiting = true;
        continueToPurchase = true;

        try
        {
            Socket server = new Socket(SERVER, PORT);
            fromServer = new DataInputStream(server.getInputStream());
            toServer = new DataOutputStream(server.getOutputStream());
    
            
            
            purchaseTickets();

            server.close();
        }
        catch (Exception e)
        {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /**
     * waits for action from buyer
     * @throws InterruptedException
     */
    private void waitForBuyersAction() throws InterruptedException
    {
        while(waiting)
        {
            Thread.sleep(100);
        }
        waiting = true;
    }
    
    /** 
     * Allows the buyer to purchase the tickets they have been given.
     * @throws IOException
     */
    private void purchaseTickets() throws IOException
    {
        toServer.writeInt(sectionsSelected);
        toServer.writeInt(numTicketsWanted);

        setChanged();
        notifyObservers(new Integer(sectionsSelected));
        notifyObservers(new Integer(numTicketsWanted));
        
        recieveInformationFromServer();

    
    }

    /**
     * Recieve information from the server
     * @throws IOException
     */
    private void recieveInformationFromServer() throws IOException
    {
        // This is where we get the buyers tickets from the sections they have
        // selected and then display it in the GUI.
        
        int concertStatus;
        int[] ticketsWantedArr;
        String[] ticketsAvaibleFromServer;
        
        if (concertStatus == TICKETS_WANTED_UNAVAILABLE)
        {
            // send the information to the GUI?
            // set observable?
        }
        else
        {
            ticketsWantedArr = new int[numTicketsWanted];
            ticketsAvailableFromServer = new String[numTicketsWanted];
            for (int i = 0; i < ticketsWantedArr.length; i++)
            {
                // read from server
            }
        }
    }

        /**if (sectionsSelected == SECTION_ONE)
        {
            SECTION_ONE = (Integer) fromServer.readInt();
            setChanged();
            notifyObservers(new Integer(SECTION_ONE));
        }
        else if (sectionsSelected == SECTION_TWO)
        {
            SECTION_TWO = (Integer) fromServer.readInt();
            setChanged();
            notifyObservers(new Integer(SECTION_TWO));
        }
        else
        {
            SECTION_THREE = (Integer) fromServer.readInt();
            setChanged();
            notifyObservers(new Integer(SECTION_THREE));
        }*/
    }
}

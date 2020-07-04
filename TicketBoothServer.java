// Team Cache Money, Albert Lopez, Jesus Crus, Jordyn Martin
// CSCI 434, Project 2, Iteration 3 
// 5/1/19
// TicketBoothServer.java
//
// Server for ticket booth client-server

import java.io.*;
import java.net.*;

/**
 * Server for the ticket booth client-server
 * @author Albert Lopez
 * @author Jesus Cruz
 * @author Jordyn Martin
 */
public class TicketBoothServer implements TicketBoothConstants
{
    private Concert[] concerts;
    private ObjectOutputStream clientOut;
    private ObjectInputStream clientIn;

    public static void main(String[] args)
    {
        new TicketBoothServer();
    }

    /**
     * Constructor of the TicketBoothServer class
     */
    public TicketBoothServer()
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            System.out.println("Server Established");

            while (true)
            {
                // Listen for a new connection request

                Socket socket = serverSocket.accept();

                clientOut = 
                    new ObjectOutputStream(socket.getOutputStream());

                clientIn = 
                    new ObjectInputStream(socket.getInputStream());

                concerts = getConcertsFromSerialized();
                clientOut.writeObject(concerts);

                Message request = (Message) clientIn.readObject();

                while (request.getMessageType() != MessageType.CLOSE_CONNECTION)
                {
                    switch(request.getMessageType())
                    {
                        case REQUESTED_SEATS:
                        {
                            RequestSeatsMessage message = (RequestSeatsMessage)
                            request;
                            handleSeatRequest(message);
                            break;
                        }
                        case PURCHASE_CONFIRM:
                        {
                            PurchaseConfirmMessage message =
                            (PurchaseConfirmMessage) request;
                            saveConcertSelections(message);

                            System.out.println("SAVED CLIENT TICKET DATA");
                            break;
                        }
                        default: 
                            System.out.println("Not implemented");
                    }
                    request = (Message) clientIn.readObject();
                }

                clientOut.close();
                clientIn.close();
                }

            }    
            catch(ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
    }

    private Concert[] getConcertsFromSerialized()
    {
        Concert[] concerts = null;

        try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(FILENAME)))
        {
            concerts = (Concert[]) inFile.readObject();
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could Not open "
                + FILENAME + " for reading.");
            System.exit(1);
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
        
        return concerts;
    }

    private void handleSeatRequest(RequestSeatsMessage message)
    {
        if (message.getSeatsRequested() == 0)
        {
            try
            {
                clientOut.writeObject(new NoSeatsSelectedError("You have not selected any seats. Please try again."));
                return;
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        Concert concert = message.getChosenConcert();
        if (concert.getSeatsAvailable() < message.getSeatsRequested())
        {
            try 
            {
                clientOut.writeObject(new SeatsSoldError("The number of tickets requested is not available."));
                return;
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // Proper input accepted, looking for available seats
        int seatsAcquired = 0;
        String seat;
        String[] ticketsToBuy = new String[message.getSeatsRequested()];
        
        // Constants for all numbers
        int sectionNumber = message.getSectionSelected();
        int sectionOneUpper = SECTION_ONE;
        int sectionTwoUpper = SECTION_TWO;
        int sectionThreeUpper = concert.getSize();
        int lower;
        int upper;


        // Determining the section to select seats in.
        if (sectionNumber == SECTION_NUM_ONE) 
        {
            lower = 0;
            upper = sectionOneUpper;
        }
        else if (sectionNumber == SECTION_NUM_TWO)
        {
            lower = sectionOneUpper;
            upper = sectionTwoUpper;
        }
        else 
        {
            lower = sectionTwoUpper;
            upper = sectionThreeUpper;
        }

        for (int i = lower; i < upper; i++)
        {
            if (i < SINGLE_DIGITS)
            {
                seat = "[0" + (i+1) +"]";
            }
            else
            {
                seat = "[" + (i+1) + "]";
            }

            if (!concert.isSeatTaken(seat) && seatsAcquired < message.getSeatsRequested())
            {
                ticketsToBuy[seatsAcquired] = seat;
                seatsAcquired++;
            }
        }
        
        if (seatsAcquired == message.getSeatsRequested())
        {
            try 
            {
                clientOut.writeObject(new SelectionSuccessMessage(ticketsToBuy));
                return;
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }   
        else
        {

            try 
            {
                clientOut.writeObject(new SeatsSoldError("The number of tickets requested is not available. Try another section"));
                return;
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
    }

    public void saveConcertSelections(PurchaseConfirmMessage message)
    {
        try (ObjectOutputStream outFile = new ObjectOutputStream( new
        FileOutputStream(FILENAME)))
        {
            Concert[] tempConcerts = message.getConcerts();

            System.out.println(message.getMessageType());
            for (Concert concert : message.getConcerts()) 
            {
                System.out.println(concert);
            }
            outFile.writeObject(tempConcerts);
            System.out.println("WROTE TO FILE");
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open the \"Venues.ser\" file");
        }
        catch (IOException e)
        {
            System.err.println("Error in writing to the \"Venues.ser\" file");
        }
    } 
}

// Team Cache Money: Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 1
// TicketBooth.java
// 4-4-19
//
// This program will create the ticket system for the users to purchase tickets
// and the concert of their choosing.

import java.io.*;
import java.util.*;

/** This will create the ticket system for the users to purchase tickets and
 * the concert of their choosing.
 * @author Albert Lopez
 * @author Jesus Crus
 * @author Jordyn Martin
 */
public class TicketBooth
{
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Welcome to the ticketBooth");
        System.out.println("");

        TicketBuyer myBuyer = new TicketBuyer("Cache");
        Concert[] concerts;
        ArrayList<Concert> openVenues = new ArrayList<Concert>();

        try (ObjectInputStream inFile = new ObjectInputStream(new
        FileInputStream("Venues.ser")))
        {
            concerts = (Concert[]) inFile.readObject();

            System.out.println("These are the current venues:");
            System.out.println("");

            int concertNum = 0;
            for (int i = 0; i < concerts.length; i++)
            {
                concertNum++;
                if (!concerts[i].isSoldOut())
                {
                    openVenues.add(concerts[i]);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open file \"Venues.ser\""
                + "for reading.");
        }
        catch (Exception e)
        {                   
            System.err.println(e);
        }

        boolean keepBuying = true;

        while(keepBuying)
        {
            int venuesSize = openVenues.size();
            for (int i = 0; i < venuesSize; i++)
            {
                System.out.println((i+1) + ": " + openVenues.get(i).getName() 
                    + " has " + openVenues.get(i).getSeatsAvailable()
                    + " tickets available.");
            }
            System.out.print("What concert would you like to like to purchase"
                + " tickets for: ");
            int con = scan.nextInt();

            while(con <= 0 || venuesSize < con )
            {
                System.out.print("That is not a valid venue, choose another: ");
                con = scan.nextInt();
            }

            System.out.println("You have choosen: " + openVenues.get(con - 1).getName());
            System.out.println("The following seats are open: ");
            System.out.println(openVenues.get(con-1).toString());

            System.out.print("How many tickets would you like to buy? ");
            int ticketNum = scan.nextInt();
            String[] tickets = new String[ticketNum];
            int purchasedTickets = 0;

            while (purchasedTickets < tickets.length)
            {
                System.out.print("Please enter the seat as seen on the screen: ");
                String seat = scan.next();
                String chosenSeat = "[" + seat + "]";
                tickets[purchasedTickets] = chosenSeat;
                purchasedTickets++;
            }
            for (int i = 0; i < tickets.length; i++)
            {
                openVenues.get(con-1).selectSeat(tickets[i]);
            }
            myBuyer.seatsPurchased(openVenues.get(con-1), tickets);
            System.out.println(myBuyer.toString());
            if (keepBuying)
            {
                System.out.print("Buy more tickets yes (Y) or no (N)? ");
                String answer = scan.next();
                keepBuying = (answer.equalsIgnoreCase("y"));
            }
        }
        System.out.println("Thank you for buying with us today!");

        try (ObjectOutputStream outFile = new ObjectOutputStream( new
            FileOutputStream("Venues.ser")))
        {
            int remainingConcerts = openVenues.size();
            for (int i = 0; i < remainingConcerts; i++)
            {
                if (openVenues.get(i).isSoldOut())
                    openVenues.remove(i);
            }

            Concert[] currentConcerts = new Concert[openVenues.size()];

            for (int i = 0; i < currentConcerts.length; i++)
            {
                currentConcerts[i] = openVenues.get(i);
            }



            System.out.println("");
            System.out.println("Writing the venues to the the file"
                + "Venues.ser");

            outFile.writeObject(currentConcerts);

            System.out.println("Finished writing the venues to \"Venues.ser\"");
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

// Team Cache Money: Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 1
// 4-4-19
// VenueAdmin.java
//
// This class allows an admin to create venues for the ticket booth.

import java.io.*;
import java.util.*;

/** This is the class allows an admin to create venues.
 * @author Albert Lopez
 * @author Jesus Crus
 * @author Jordyn Martin
 */
public class VenueAdmin
{
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {
        int venueNum = 0;

        System.out.println("Hello, welcome to the concert ticket venue system.");
        System.out.println("");

        System.out.println("To get started enter the number of concerts you ");
        System.out.print("would like to create, use only numbers: ");
        while(!scan.hasNextInt())
        {
            System.out.println("That is not a valid number");
            System.out.print("Enter a valid number: ");
            scan.next();
        }

        venueNum = scan.nextInt();
        Concert[] concerts = new Concert[venueNum];

        int currentVenue = 0;
        while (currentVenue < venueNum)
        {
            System.out.println("");
            System.out.print("Enter the name of venue: ");
            String name = scan.next();

            System.out.print("Enter the number of seats in each row: ");
            while(!scan.hasNextInt())
            {
                System.out.println("That is not a valid number");
                System.out.print("Enter a valid number: ");
                scan.next();
            }
            int col = scan.nextInt();

            System.out.print("Enter the number of rows: ");
            while(!scan.hasNextInt())
            {
                System.out.println("That is not a valid number");
                System.out.print("Enter a valid number: ");
                scan.next();
            }
            int row = scan.nextInt();
            concerts[currentVenue] = new Concert(row, col, name);

            System.out.println("Created the venue for: " + concerts[currentVenue].getName());
            currentVenue++; 
        }
        int i;

        try (ObjectOutputStream outFile = new ObjectOutputStream( new
            FileOutputStream("Venues.ser")))
        {
            System.out.println("");
            System.out.println("Writing the venues to the the file"
                + "Venues.ser");
            
            outFile.writeObject(concerts);
            
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

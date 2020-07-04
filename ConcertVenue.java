// Team Cache Money: Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 1
// 4/1/2019
// ConcertVenue.java
// 
// This is the class to represent a concert venue item.
import java.io.Serializable;

/** This is the class to represent a concert venue item.
 * @author Albert Lopez
 * @author Jesus Crus
 * @author Jordyn Martin
 */
public class ConcertVenue implements Serializable
{
    private int venueSize;
    private int row;
    private int col;
    private String venueName;
    private String[][] venueMap;
    private int availableSeats;
    private int seatNum;

    /** Creates the concert venue map.
     * @param row Creates an int for the rows in the concert.
     * @param col Creates an int for the columns in the concert.
     * @param name Takes the venues name.
     */
    public ConcertVenue(int row, int col, String name)
    {
        row = row;
        col = col;
        venueName = name;
        venueMap = new String[row][col];
        venueSize = row * col;
        availableSeats = row * col;
        seatNum = 1;

        for (int i = 0; i < venueMap.length; i++)
        {
            for (int j = 0; j < venueMap[i].length; j++)
            {
                if (seatNum <= 9)
                {
                    venueMap[i][j] = "[0" + seatNum + "]";
                    seatNum++;
                }
                else
                {
                    venueMap[i][j] = "[" + seatNum + "]";
                    seatNum++;
                }
            }
        }
    }

    /** Gets the name of the venue.
     * @return Returns the venue name that the user selected.
     */
    public String getName()
    {
        return venueName;
    }

    /** Tells the user if their are available seats and if so which ones.
     * @return Returns the availble seats for purchasing.
     */
    public int getSeatsAvailable()
    {
        return availableSeats;
    }

    /** Allows the user to select the seat that they would like to purchase.
     * @param seat Takes the seat row and column that they would like to
     * purchase.
     */
    public void selectSeat(String seat)
    {
        if (!isSoldOut())
        {
            for (int i = 0; i < venueMap.length; i++)
            {
                for (int j = 0; j < venueMap[i].length; j++)
                {
                    if (seat.equals(venueMap[i][j]))
                    {
                        venueMap[i][j] = "[X]";
                        availableSeats--;
                    }
                }
            }
        }
    }

    /** Checks to see if the venue is sold out or not.
     * @return Returns if the available seats is zero.
     */
    public boolean isSoldOut()
    {
        return availableSeats == 0;
    }

    /** Gets the size of the venue.
     * @return Returns the size of the venue.
     */
    public int getSize()
    {
        return venueSize;
    }

    /** Creates the toString for the concert venue.
     * @return Returns the map of the concert for the user.
     */
    public String toString()
    {
        String map = "";

        for (int i = 0; i < venueMap.length; i++)
        {
            for (int j = 0; j < venueMap[i].length; j++)
            {
                map += venueMap[i][j];
            }
            map += "\n";
        }

        return map;
    }
}

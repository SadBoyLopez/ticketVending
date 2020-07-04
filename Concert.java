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
public class Concert implements Serializable
{
    private int concertSize;
    private int numOfConcertRows;
    private int numOfConcertCols;
    private String concertName;
    private String[][] concertMap;
    private int availableSeats;
    private int seatNum;

    /** Creates the concert venue map.
     * @param row Creates an int for the rows in the concert.
     * @param col Creates an int for the columns in the concert.
     * @param name Takes the venues name.
     */
    public Concert(int row, int col, String name)
    {
        numOfConcertRows = row;
        numOfConcertCols = col;
        concertName = name;
        concertMap = new String[row][col];
        concertSize = row * col;
        availableSeats = row * col;
        seatNum = 1;

        for (int i = 0; i < concertMap.length; i++)
        {
            for (int j = 0; j < concertMap[i].length; j++)
            {
                if (seatNum <= 9)
                {
                    concertMap[i][j] = "[0" + seatNum + "]";
                    seatNum++;
                }
                else
                {
                    concertMap[i][j] = "[" + seatNum + "]";
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
        return concertName;
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
            for (int i = 0; i < concertMap.length; i++)
            {
                for (int j = 0; j < concertMap[i].length; j++)
                {
                    if (seat.equals(concertMap[i][j]))
                    {
                        if (!isSeatTaken(seat))
                        {
                            concertMap[i][j] = "[X]";
                            availableSeats--;
                        }
                    }
                }
            }
        }
    }
    
    /** Checks to see if the current seat is taken.
     * @return Returns if the seat is taken or not.
     */
    public boolean isSeatTaken(String seat)
    {
        int seatNum = 1;
        String tempSeat = "";

        boolean seatTaken = false;
        for (int i = 0; i < concertMap.length; i++)
        {
            for (int j = 0; j < concertMap[i].length; j++)
            {
                if (seatNum <= 9)
                {
                    tempSeat = "[0" + seatNum + "]";
                    if (tempSeat.equals(seat))
                    {
                        if(concertMap[i][j].equals("[X]"))
                            seatTaken = true;
                    }
                    seatNum++;
                }
                else
                {
                    tempSeat = "[" + seatNum + "]";
                    if (tempSeat.equals(seat))
                    {
                        if(concertMap[i][j].equals("[X]"))
                            seatTaken = true;
                    }
                    seatNum++;
                }
            }
        }

        return seatTaken;
    }


    /** Checks to see if the venue is sold out or not.
     * @return Returns if the available seats is zero.
     */
    public boolean isSoldOut()
    {
        return availableSeats == 0;
    }

    /** Gets the number of rows.
     * @return Returns the number of rows for the concert.
     */
    public int getRows()
    {
        return numOfConcertRows;
    }

    /** Gets the number of columns.
     * @return Returns the number of columns for the concert.
     */
    public int getCols()
    {
        return numOfConcertCols;
    }

    /** Gets the size of the venue.
     * @return Returns the size of the venue.
     */
    public int getSize()
    {
        return concertSize;
    }

    /** Creates the toString for the concert venue.
     * @return Returns the map of the concert for the user.
     */
    public String toString()
    {
        String map = "";

        for (int i = 0; i < concertMap.length; i++)
        {
            for (int j = 0; j < concertMap[i].length; j++)
            {
                map += concertMap[i][j];
            }
            map += "\n";
        }

        return map;
    }
}

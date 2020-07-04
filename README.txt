Albert Lopez, Jesus Cruz, Jordyn Martin
CSCI 434, Project 2, Iteration 3
4/4/2019
README.txt

Classes:

    ConcertVenue.java
    TicketBooth.java
    TicketBuyer.java
    VenueAdmin.java
    TicketBoothGUI.java
    TicketBoothServer.java
    TicketBoothConstants.java

Class Descriptions:

                                    Iteration 1: Text Base
    ConcertVenue.java
        - ConcertVenue(int row, int col, String name)
            "Initializes the concert venue with the size of the venue, the row
            and column, and the name of the venue along with the available
            seats, and the initial seat number. The nested for loop sets up a
            visual representation of the venue, single digit numbers have a "0"
            placed infront of them to make the representation more visual
            appealing"

        - getName()
            "Returns the name of the venue"

        - selectSeat(String seat)
            "While the venue is not sold out, we search through the 2D array to
            show available seats for the user to purchase."

        - isSoldOut()
            "Checks to see if the available seats count is at 0 to tell the
            user that the concert is sold out."

        - getSize()
            "Returns the venue size."

        - toString()
            "Creates the venues map showing the rows and columns for the user
            to select from."


    TicketBooth.java
        - "Creates the Ticket system that the users will see to purchase their
          tickets. With this they will be able to select the venue they would
          like, the amount of tickets they would like to purchase (changes prices 
          depending on how close the row is to the front) and then check out
          and pay."
    
    TicketBuyer.java
        - TicketBuyer(String name)
            "Initializes the buyers name, number of tickets to zero, and
            creates a HashMap for the concert to hold an array list of the
            venues available."

        - ticketCount(int tickets)
            "Adds the number of tickets the user has bought to the numTickets
            count."

        - getTicketCount()
            "Returns the number of tickets the user has purchased."

        - seatsPurchased(String venueName, String[] seats)
            "Checks the key in the HashMap and returns the arrayList for the
            concert venue based off the String venueName. If the key is in the
            map, the seats will be added to the list for the user to see."

        - toString()
            "Shows the user the amount of tickets they have bought so far or if
            they haven't bought tickets yet."
    
    VenueAdmin.java
        - "Runs the program from an Admin point of view so that they can create
          the concerts that the user will see. It will check the number of
          seats in each row and column that the Admin wants to add and if they
          are valid numbers to add."

          TO RUN:
          java TicketBooth

                                    Iteration 2: GUI

    TicketBoothGUI.java
        - "Creates the GUI for the Concert Ticket Simulation program. This will
          allow users to select how many seats, what concert and also see if
          the concert is full or not."

        - readSerFile()
            "Reads from the .ser file and creates the buttons for the GUI."
        
        - CreateSeatGUI()
            "Checks if the seat is taken or not and then creates a visual
            representation of an open or taken seat for the user to see."

        - getNumTicketsToPurchase()
            "Converts the number of tickets taken from the dialog box to an
            int."

        - updateSeatsGUI()
            "Updates the visual representation of the seats to taken instead of
            the open image."

        TO RUN:
        java TicketBoothGUI

                                Iteration 3: Client-Server GUI


    TicketBoothServer.java
        -"Creates the server for the ticket booth client-server iteration."

        - TicketBoothServer()
            "Constructor for the TicketBoothServer class that allows them to
            create a connection to be requested."
        
        - getConcertsFromSerialized()
            "Gets the ticket information from the serializable file to keep
            information up to date."

        - handleSeatRequest(RequestSeatsMessage message)
            "Handles the seat(s) requested by the buyer and gives to correct
            error message if any were to occur."

        - saveConcertSelections(PurchaseConfirmMessage message)
            "Writes to the serializable file with the saved concert information
            and will send errors if file cannot be opened or cannot be written
            to."


    TicketBoothGUI.java
        -"Creates the server task for the ticket booth client-server."

        -TicketBoothGUI()
            "Constructor for the TicketBoothGUI so that the buyer can
            establish a connection to the server and create the GUI for the
            user to see."
        
        - processConcerts(Concert[] inputConcerts)
            "Processes the concerts that have been created from the
            serializable file and give the user an updated concert list."

        - getNumTicketsToPurchase()
            "Recieves the number of tickets that the user wants to purchase and
            then returns it."


    TicketBoothConstants.java
        -"Creates the constants used for the server, server task, and client.
        This helps with redundancy when naming different values."

    TO RUN:
        java TicketBoothServer
        java TicketBoothGUI

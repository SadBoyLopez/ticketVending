// Team Cache Money: Albert Lopez, Jesus Cruz, Jordyn Martin
// CSCI 434, Project 2, Iteration 1
// TicketBooth.java
// 4-4-19
//
// This program will create the ticket system for the users to purchase tickets
// and the concert of their choosing.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*; 
import java.net.*;


public class TicketBoothGUI extends JFrame implements TicketBoothConstants
{
    public static final int WIDTH = 1150;
    public static final int HEIGHT = 860;
    private static final String FILENAME = "Venues.ser";
    private static final int ROWS = 1;
    private static final ImageIcon VENUE = new ImageIcon("venue_layout.png");
    private JLabel stageIcon;

    private Socket socket;
    private ObjectInputStream serverIn;
    private ObjectOutputStream serverOut;

    private Concert[] concerts;
    private JButton[] eventButtons;
    private JButton[] sectionButtons;
    private JPanel seatLayoutPanel;
    private Concert currentConcert;

    private JButton confirmPurchase;

    private int chosenSection;
    private int sectionOneTickets;
    private String[] ticketsToBuy;

    private TicketBuyer myBuyer = new TicketBuyer("Cache");
    private JLabel ticketBuyerLabel = new JLabel();

    //private HashMap<Concert, JLabel[][]> seatLayoutHashMap;

    public static void main(String[] args)
    {
        TicketBoothGUI gui = new TicketBoothGUI();
        gui.setVisible(true);
    }

    public TicketBoothGUI()
    {
        super("Ticket Booth");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                
                if (serverOut != null) {
                    try 
                    {
                        serverOut.writeObject(new CloseConnectionMessage());
                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }
                }
            }
        });

        try
        {
            socket = new Socket("localhost", PORT);
            
            serverOut = new ObjectOutputStream(socket.getOutputStream());
            serverIn = new ObjectInputStream(socket.getInputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        try
        {
            // Response should be the concert array.
            Concert[] response = (Concert[]) serverIn.readObject();
            processConcerts(response);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        
        JPanel concertButtonPanel = new JPanel(new GridLayout(ROWS,
            concerts.length));
        JPanel sectionButtonPanel = new JPanel(new GridLayout(3,1));

        for (int i = 0; i < eventButtons.length; i++)
        {
            concertButtonPanel.add(eventButtons[i]);
            eventButtons[i].addActionListener(new ConcertButtonListener());
        }

        sectionButtons = new JButton[3];
        sectionButtons[0] = new JButton("Section 1: Seats 1 - 20");
        sectionButtons[1] = new JButton("Section 2: Seats 21 - 50");
        sectionButtons[2] = new JButton("Section 3: Seats 50 - Rest of Seats");

        for (int i = 0; i < sectionButtons.length;i++)
        {
            sectionButtons[i].setEnabled(false);
            sectionButtonPanel.add(sectionButtons[i]);
            sectionButtons[i].addActionListener(new SectionButtonListener());
        }

        Font ticketBuyerFont = new Font("Serif", Font.BOLD, 25);
        ticketBuyerLabel.setFont(ticketBuyerFont);

        ticketBuyerLabel.setText("You have not purchased any tickets yet");
        JPanel ticketBuyerStatus = new JPanel();
        ticketBuyerStatus.add(ticketBuyerLabel);

        confirmPurchase = new JButton("Confirm purchase");
        confirmPurchase.addActionListener(new PurchaseButtonListener());

        stageIcon = new JLabel();
        stageIcon.setIcon(VENUE);
        seatLayoutPanel = new JPanel();
        seatLayoutPanel.add(stageIcon);

        JPanel purchasePanel = new JPanel();
        purchasePanel.add(confirmPurchase);
        

        add(concertButtonPanel, BorderLayout.NORTH);
        add(seatLayoutPanel, BorderLayout.CENTER);
        add(sectionButtonPanel, BorderLayout.WEST);
        add(ticketBuyerStatus, BorderLayout.SOUTH);
        add(purchasePanel, BorderLayout.EAST);
        pack();
    }

    private void processConcerts(Concert[] inputConcerts)
    {
        concerts = new Concert[inputConcerts.length];
        eventButtons = new JButton[inputConcerts.length];

        for (int i = 0; i < inputConcerts.length; i++)
        {
            concerts[i] = inputConcerts[i];
            eventButtons[i] = new JButton(concerts[i].getName());
        }
    }

    private class PurchaseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try
            {
                for (Concert concert: concerts) {
                }
                PurchaseConfirmMessage confirmation = new
                    PurchaseConfirmMessage(concerts);
                
                for (Concert concert : confirmation.getConcerts())
                {
                    System.out.println(concert);
                }
            
                System.out.println(myBuyer.toStringComplete());
                serverOut.writeObject(confirmation);
            }
            catch (IOException i)
            {
                System.err.println(i);
            }

            ticketBuyerLabel.setText("Thank you for purchasing with us today!");
        
            for (int i = 0; i < sectionButtons.length;i++)
            {
                sectionButtons[i].setEnabled(false);
            }

            for (int i = 0; i < eventButtons.length; i++)
            {
                eventButtons[i].setEnabled(false);
            }
        }
    }

    private class ConcertButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            Object buttonPressed = e.getSource();

            for (int i = 0; i < eventButtons.length; i++)
            {
                if (buttonPressed == eventButtons[i])
                {
                    currentConcert = concerts[i];
                }
            }
            for (int i = 0; i < sectionButtons.length;i++)
            {
                sectionButtons[i].setEnabled(true);
            }
        }
    }

    private class SectionButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object buttonPressed = e.getSource();

            for (int i = 0; i < sectionButtons.length; i++)
            {
                if (buttonPressed == sectionButtons[i])
                    chosenSection = i;
            }
            
            int numTicketsForConcert = getNumTicketsToPurchase();

            try
            {
                RequestSeatsMessage requestSeats = new
                RequestSeatsMessage(currentConcert, chosenSection,
                numTicketsForConcert);

                serverOut.writeObject(requestSeats);
                Message response = (Message) serverIn.readObject();

                if (response.getMessageType() == MessageType.SEATS_SOLD_ERROR) 
                {
                    SeatsSoldError errorMessage = (SeatsSoldError) response;
                    JOptionPane.showMessageDialog(null,
                    errorMessage.getErrorMessage());
                }

                if (response.getMessageType() == MessageType.NO_SEATS_SELECTED_ERROR)
                {
                    NoSeatsSelectedError errorMessage = (NoSeatsSelectedError)
                    response;
                    JOptionPane.showMessageDialog(null,
                    errorMessage.getErrorMessage());
                }

                if (response.getMessageType() == MessageType.SELECTION_SUCCESS)
                {
                    SelectionSuccessMessage message = (SelectionSuccessMessage)
                    response;
                    
                    String[] tickets = message.getTicketsToBuy();
                    for (int i = 0; i < tickets.length; i++)
                    {
                        currentConcert.selectSeat(tickets[i]);
                    }

                    myBuyer.seatsPurchased(currentConcert, tickets);
                    ticketBuyerLabel.setText(myBuyer.toString());
                }
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
            catch(ClassNotFoundException c)
            {
                System.out.println(c);
            }
        }

    }

    /**
    * This will get the number of tickets that the user has purchased and
    * return it.
    * @return number of tickets
    */
    public int getNumTicketsToPurchase()
    {
        int numOfTickets= Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tickets to purchase"));
        return numOfTickets;
    }
}

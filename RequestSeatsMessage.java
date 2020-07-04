import java.io.Serializable;

public class RequestSeatsMessage implements Message, Serializable
{
    private MessageType type;
    private Concert chosenConcert;
    private int sectionSelected;
    private int seatsRequested;

    public RequestSeatsMessage(Concert chosenConcert, int sectionSelected, int seatsRequested)
    {
        this.type = MessageType.REQUESTED_SEATS;
        this.chosenConcert = chosenConcert;
        this.sectionSelected = sectionSelected;
        this.seatsRequested = seatsRequested;
    }

    public MessageType getMessageType()
    {
        return type;
    }

    public Concert getChosenConcert()
    {
        return chosenConcert;
    }

    public int getSectionSelected()
    {
        return sectionSelected;
    }

    public int getSeatsRequested()
    {
        return seatsRequested;
    }
}


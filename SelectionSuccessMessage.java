import java.io.Serializable;

public class SelectionSuccessMessage implements Message, Serializable
{
    private MessageType type;
    private String[] ticketsToBuy;

    public SelectionSuccessMessage(String[] ticketsToBuy)
    {
        this.type = MessageType.SELECTION_SUCCESS;
        this.ticketsToBuy = ticketsToBuy;
    }

    public MessageType getMessageType()
    {
        return type;
    }

    public String[] getTicketsToBuy()
    {
        return ticketsToBuy;
    }
}


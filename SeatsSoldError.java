import java.io.Serializable;

public class SeatsSoldError implements Message, Serializable
{
    private MessageType type;
    private String errorMessage;

    public SeatsSoldError(String errorMessage)
    {
        this.type = MessageType.SEATS_SOLD_ERROR;
        this.errorMessage = errorMessage;
    }

    public MessageType getMessageType()
    {
        return type;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
}


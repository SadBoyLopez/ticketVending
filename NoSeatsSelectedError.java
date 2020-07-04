import java.io.Serializable;

public class NoSeatsSelectedError implements Message, Serializable
{
    private MessageType type;
    private String errorMessage;

    public NoSeatsSelectedError(String errorMessage)
    {
        this.type = MessageType.NO_SEATS_SELECTED_ERROR;
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

import java.io.Serializable;

public class CloseConnectionMessage implements Message, Serializable
{
    private MessageType type;

    public CloseConnectionMessage()
    {
        this.type = MessageType.CLOSE_CONNECTION;
    }

    public MessageType getMessageType()
    {
        return type;
    }
}


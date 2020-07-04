import java.io.Serializable;

public class PurchaseConfirmMessage implements Message, Serializable
{
    private MessageType type;
    private Concert[] concerts;

    public PurchaseConfirmMessage(Concert[] concerts)
    {
        this.type = MessageType.PURCHASE_CONFIRM;
        this.concerts = concerts;
    }

    public MessageType getMessageType()
    {
        return type;
    }

    public Concert[] getConcerts()
    {
        return concerts;
    }
}


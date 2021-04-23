import java.util.ArrayList;

public class CardPay extends Card{
    private int quantity;

    public CardPay (String type, boolean postposable) {
        super(type,postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board,int quantity) {

    }
}

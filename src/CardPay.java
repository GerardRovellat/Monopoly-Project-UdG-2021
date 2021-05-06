import java.util.ArrayList;

public class CardPay extends Card{
    private int quantity;

    public CardPay ( boolean postposable, int quantity) {
        super("PAY",postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board) {

    }
}

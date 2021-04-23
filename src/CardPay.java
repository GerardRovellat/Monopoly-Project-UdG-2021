import java.util.ArrayList;

public class CardPay extends Card{
    private int quantity;

    public CardPay (String type, boolean postposable, ArrayList<Player> players, Board board,int quantity) {
        super(type,postposable,players,board);
        this.quantity = quantity;
    }

    public void execute() {

    }
}

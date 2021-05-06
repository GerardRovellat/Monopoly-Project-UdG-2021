import java.util.ArrayList;

public class CardCharge extends Card{
    private int quantity;

    public CardCharge (boolean postposable, int quantity) {
        super("CHARGE",postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board) {

    }
}

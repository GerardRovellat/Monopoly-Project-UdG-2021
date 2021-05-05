import java.util.ArrayList;

public class CardCharge extends Card{
    private int quantity;

    public CardCharge (String type, boolean postposable, int quantity) {
        super(type,postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board) {

    }
}

import java.util.ArrayList;

public class CardFine extends Card{
    private int quantity;

    public CardFine (boolean postposable, int quantity) {
        super("FINE",postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board) {

    }
}

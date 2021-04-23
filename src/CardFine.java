import java.util.ArrayList;

public class CardFine extends Card{
    private int quantity;

    public CardFine (String type, boolean postposable, int quantity) {
        super(type,postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board,int quantity) {

    }
}

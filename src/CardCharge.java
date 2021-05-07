import java.util.ArrayList;
import java.util.HashMap;

public class CardCharge extends Card{
    private int quantity;
    private HashMap<Integer,String> messages = new HashMap<>();

    public CardCharge (boolean postposable, int quantity) {
        super("CHARGE",postposable);
        this.quantity = quantity;
        messages.put(0,"PAGA " + quantity + " € POR UN FIN DE SEMANA EN UN BALEARIO DE 5 ESTRELLAS");
    }

    public void execute(ArrayList<Player> players, Board board) {

    }
}

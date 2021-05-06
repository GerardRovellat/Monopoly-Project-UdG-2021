import java.util.ArrayList;

public class CardPay extends Card{
    private int quantity;

    public CardPay ( boolean postposable, int quantity) {
        super("PAY",postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board) {
        System.out.println("Has de pagar "+quantity+" a un dels altres jugadors");
        int option_nr = 1;
        for (Player player : players){
            System.out.println(option_nr+"- "+player.getName()+" ("+player.getMoney()+")");

        }

    }
}

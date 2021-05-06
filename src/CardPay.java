import java.util.ArrayList;
import java.util.Scanner;

public class CardPay extends Card{
    private int quantity;

    public CardPay ( boolean postposable, int quantity) {
        super("PAY",postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board, Player actual_player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Has de pagar "+quantity+" a un dels altres jugadors");
        int option_nr = 1;
        for (Player player : players){
            if (actual_player != player) {
                System.out.println(option_nr + "- " + player.getName() + " (" + player.getMoney() + ")");
            }
            option_nr++;
        }
        System.out.println("A quin jugador vols pagar?");
        option_nr = scanner.nextInt();
        actual_player.pay(quantity);
        players.get(option_nr-1).charge(quantity);
        System.out.println("El jugador "+actual_player.getName()+" ha pagat "+quantity+"€ a "+players.get(option_nr-1).getName());
        //FALTA TRACTAR QUAN NO POT FER EL PAGAMENT
    }
}

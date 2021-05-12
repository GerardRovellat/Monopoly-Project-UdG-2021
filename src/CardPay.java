import java.util.ArrayList;
import java.util.Scanner;

public class CardPay extends Card{
    private int quantity;

    public CardPay ( boolean postposable, int quantity) {
        super("PAY",postposable);
        this.quantity = quantity;
    }

    public void execute(ArrayList<Player> players, Board board, Player current_player, Movement aux) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Has de pagar "+quantity+" a un dels altres jugadors");
        if (current_player.getMoney() >= quantity) {
            int option_nr = 0;
            for (Player player : players) {
                if (current_player != player) {
                    System.out.println(option_nr + "- " + player.getName() + " (" + player.getMoney() + ")");
                }
                option_nr++;
            }
            System.out.println("A quin jugador vols pagar?");
            option_nr = scanner.nextInt();
            while (option_nr < 0 || option_nr > players.size() - 1 || players.indexOf(current_player) == option_nr) { // POSIBLE ERROR AQUI
                System.out.println("Valor entrat erroni, torni a provar");
                option_nr = scanner.nextInt();
            }
            current_player.pay(quantity);
            players.get(option_nr).charge(quantity);
            System.out.println("El jugador " + current_player.getName() + " ha pagat " + quantity + "€ a " + players.get(option_nr).getName());
        }
        else {
            System.out.println("No tens suficient diners per pagar");
            board.isBankrupt(current_player,quantity,aux);
        }
        //FALTA TRACTAR QUAN NO POT FER EL PAGAMENT
    }

    public String toString(){
        return "Has de pagar "+quantity+" a un dels altres jugadors";
    }

}

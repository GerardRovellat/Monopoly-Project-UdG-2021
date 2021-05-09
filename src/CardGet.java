import java.util.ArrayList;
import java.util.Scanner;

public class CardGet extends Card{

    public CardGet (boolean postposable) {
        super("GET",postposable);
    }

    public void execute(ArrayList<Player> players, Board board, Player actual_player) {
        Scanner scan = new Scanner(System.in);
        System.out.println("El jugador rebra una propietats d'algun dels seus adversaris");
        System.out.println("Seleccioni el jugador");
        int option_nr = 1;
        for (Player player : players){
            if (actual_player != player) {
                System.out.println(option_nr + "- " + player.getName());
            }
            option_nr++;
        }
    }

    public String toString(){
        return "El jugador rebra una propietats d'algun dels seus adversaris";
    }
}

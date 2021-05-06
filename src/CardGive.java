import java.util.ArrayList;
import java.util.Scanner;

public class CardGive extends Card{

    public CardGive ( boolean postposable) {
        super("GIVE",postposable);
    }

    public void execute(ArrayList<Player> players,Board board, Player actual_player) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Estàs obligat a donar inmediatament una de les teves propietats");
        System.out.println("Seleccioni el jugador");
        int x = 0;
        for (Player aux : players) {
            if (actual_player != aux) {
                System.out.println(x + ". " + aux.getName());
            }
            x++;
        }
        int value = -1;  // CANVIAR COM A CARDPAY
        Player chose_player = null;
        while (value < 0 || value > x) {
            value = scan.nextInt();
            if (value >= 0 && value <= x) {
                chose_player = players.get(value);
            }
            else System.out.println("Valor entrat erroni, torni a provar");
        }

        System.out.println("Seleccioni la propietat");
        x = 0;
        for (Box properties : actual_player.getBoxes()) {
            Field temp = (Field) properties;
            System.out.println(x + ". " + temp.getName()); //
            x++;
        }
        value = -1;
        Field chose_box = null;
        while (value < 0 || value > x) {
            value = scan.nextInt();
            if (value >= 0 && value <= x) {
                chose_box = (Field) actual_player.getBoxes().get(value);
            }
            else System.out.println("Valor entrat erroni, torni a provar");
        }

        System.out.println("Es traspassarà la propietat " + chose_box.getName() + " a el jugador " + chose_player.getName() + ".");
        actual_player.removeBox(chose_box);
        chose_player.addBox(chose_box);
        System.out.println("Propietat traspassada");
    }
}

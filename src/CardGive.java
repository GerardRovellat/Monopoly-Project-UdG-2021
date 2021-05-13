import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Marc Got
 * @file CardGive.java
 * @class CardGive
 * @brief Implementa la funcions de la carta de tipus donar
 */

public class CardGive extends Card{

    /**
     * @brief Constructor de CardGive
     * @pre \p true
     * @post La carta ha estat creada
     * @param postposable true si es posposable, false altrament
     */
    public CardGive ( boolean postposable) {
        super("GIVE",postposable);
    }

    /**
     * @brief Execucio de la carta
     * @pre \p true
     * @post la propietat ha estat donada
     * @param players llista de jugadors de la partida
     * @param board taulell
     * @param current_player jugador actiu
     */
    public void execute(ArrayList<Player> players,Board board, Player current_player) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Estàs obligat a donar inmediatament una de les teves propietats");
        if (current_player.getFields().size() > 0) {
            System.out.println("Seleccioni el jugador");
            int x = 0;
            for (Player aux : players) {
                if (current_player != aux) {
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
                } else System.out.println("Valor entrat erroni, torni a provar");
            }
            // SI NO HI HAN PROPIETATS, NO CAL ENTRAR
            System.out.println("Seleccioni la propietat");
            x = 0;
            for (Box properties : current_player.getFields()) {
                Field temp = (Field) properties;
                System.out.println(x + ". " + temp.getName()); //
                x++;
            }
            value = -1;
            Field chose_box = null;
            while (value < 0 || value > x) {
                value = scan.nextInt();
                if (value >= 0 && value <= x) {
                    chose_box = (Field) current_player.getFields().get(value);
                } else System.out.println("Valor entrat erroni, torni a provar");
            }

            System.out.println("Es traspassarà la propietat " + chose_box.getName() + " a el jugador " + chose_player.getName() + ".");
            current_player.removeBox(chose_box);
            chose_player.addBox(chose_box);
            System.out.println("Propietat traspassada");
        }
        else System.out.println("No disposes de cap propietat");

    }
    
    /**
     * @brief Sortida
     * @pre \p true
     * @post el missatge ha estat retornat
     * @return missatge de sortida
     */
    public String toString(){
        return "Estàs obligat a donar inmediatament una de les teves propietats";
    }

}

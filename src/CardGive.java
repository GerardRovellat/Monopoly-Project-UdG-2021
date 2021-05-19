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
        System.out.println("Has de donar una propietats d'algun dels seus adversaris");
        if (current_player.haveFields()) {
            int option_nr = 0;
            ArrayList<Integer> not_disponible = new ArrayList<>();
            for (Player player : players) {
                if (current_player != player) {
                    System.out.println(option_nr + "- " + player.getName());
                } else {
                    not_disponible.add(option_nr);
                }
                option_nr++;
            }
            try {
                System.out.println("Seleccioni el jugador");
                option_nr = current_player.optionSelection("cardGivePlayerSelect", null, null, not_disponible, players, null,0,null);
                while (not_disponible.contains(option_nr)) {
                    System.out.println("Error, sel·leccioni un jugador de la llista");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format incorrecte. Torna-hi.");
            }
            Player choosed = players.get(option_nr);
            int field_nr = 0;
            for (Field field : current_player.getFields()) {
                System.out.println(field_nr + "- " + field.getName() + " (" + field.getPrice() + ")");
                field_nr++;
            }
            try {
                System.out.println("Seleccioni un terreny");
                field_nr = current_player.optionSelection("cardGiveFieldSelect", current_player, null, null, null, null,0,null);
                while (field_nr < 0 && field_nr > current_player.getFields().size()) {
                    System.out.println("Error, sel·leccioni un terreny correcte");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format incorrecte. Torna-hi.");
            }

            Field field_choosed = current_player.getFields().get(field_nr);
            choosed.addBox(field_choosed);
            current_player.removeBox(field_choosed);
            System.out.println("En " + current_player.getName() + " ha hagut de donar " + field_choosed.getName() + " a " + choosed.getName());
        }
        else System.out.println("No tens cap propietat i per tant, no en pots donar cap");
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

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
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Estàs obligat a donar inmediatament una de les teves propietats");
        if (current_player.getFields().size() > 0) {
            System.out.println("Seleccioni el jugador");
            int x = 0;
            ArrayList<Integer> not_disponible = new ArrayList<>();
            for (Player aux : players) {
                if (current_player != aux && aux.haveFields()) {
                    System.out.println(x + ". " + aux.getName());
                }
                else not_disponible.add(players.indexOf(aux));
                x++;
            }
            int value = -1;  // CANVIAR COM A CARDPAY
            Player chose_player = null;
            while (value < 0 || value > x) {
                //value = scan.nextInt();
                value = current_player.optionSelection("cardGivePlayerSelect",null,null,not_disponible,players,null);
                if (value >= 0 && value <= x) {
                    chose_player = players.get(value);
                } else System.out.println("Valor entrat erroni, torni a provar");
            }
            System.out.println("Seleccioni la propietat");
            x = 0;
            for (Field properties : current_player.getFields()) {
                System.out.println(x + ". " + properties.getName()); //
                x++;
            }
            value = -1;
            Field chose_box = null;
            while (value < 0 || value > current_player.getFields().size()-1) {
                value = current_player.optionSelection("cardGiveFieldSelect",current_player,null,null,null,null);
                if (value >= 0 && value <= x) {
                    chose_box = current_player.getFields().get(value);
                } else System.out.println("Valor entrat erroni, torni a provar");
            }

            System.out.println("Es traspassarà la propietat " + chose_box.getName() + " a el jugador " + chose_player.getName() + ".");
            current_player.removeBox(chose_box);
            chose_player.addBox(chose_box);
            System.out.println("Propietat traspassada");
        }
        else System.out.println("No disposes de cap propietat");*/


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
                //option_nr = scan.nextInt();
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
                //field_nr = scan.nextInt();
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

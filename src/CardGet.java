import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Gerard Rovellat
 * @file CardGet.java
 * @class CardGet
 * @brief Implementa la funcions de la carta de tipus rebre
 */

public class CardGet extends Card{

    /**
     * @brief Constructor de CardGet
     * @pre \p true
     * @post La carta ha estat creada
     * @param postposable true si es posposable, false altrament
     */
    public CardGet (boolean postposable) {
        super("GET",postposable);
    }

    /**
     * @brief Execucio de la carta
     * @pre \p true
     * @post la propietat ha estat rebuda
     * @param players llista de jugadors de la partida
     * @param board taulell
     * @param current_player jugador actiu
     */
    public void execute(ArrayList<Player> players, Board board, Player current_player) {
        Scanner scan = new Scanner(System.in);
        System.out.println("El jugador rebra una propietats d'algun dels seus adversaris");
        int option_nr = 0;
        ArrayList<Integer> not_disponible = new ArrayList<>();
        for (Player player : players){
            if (current_player != player && player.haveFields() ) {
                System.out.println(option_nr + "- " + player.getName());
            }
            else{
                not_disponible.add(option_nr);
            }
            option_nr++;
        }
        if (players.size() != not_disponible.size()) {
            try {
                System.out.println("Seleccioni el jugador");
                //option_nr = scan.nextInt();
                option_nr = current_player.optionSelection("cardGetPlayerSelect", null, null, not_disponible, players, null,0,null);
                while (not_disponible.contains(option_nr)) {
                    System.out.println("Error, sel·leccioni un jugador de la llista");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format incorrecte. Torna-hi.");
            }
            Player choosed = players.get(option_nr);
            int field_nr = 0;
            for (Field field : choosed.getFields()) {
                System.out.println(field_nr + "- " + field.getName() + " (" + field.getPrice() + ")");
                field_nr++;
            }
            try {
                System.out.println("Seleccioni un terreny");
                //field_nr = scan.nextInt();
                field_nr = current_player.optionSelection("cardGetFieldSelect", choosed, null, null, null, null,0,null);
                while (field_nr < 0 && field_nr > choosed.getFields().size()) {
                    System.out.println("Error, sel·leccioni un terreny correcte");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format incorrecte. Torna-hi.");
            }
            Field field_choosed = choosed.getFields().get(field_nr);
            current_player.addBox(field_choosed);
            choosed.removeBox(field_choosed);
            System.out.println("En " + current_player.getName() + " ha adquirit " + field_choosed.getName());
        }
        else System.out.println("No hi ha cap jugador amb propietats per rebre");
    }

    /**
     * @brief Sortida
     * @pre \p true
     * @post el missatge ha estat retornat
     * @return missatge de sortida
     */
    public String toString(){
        return "El jugador rebra una propietats d'algun dels seus adversaris";
    }
}

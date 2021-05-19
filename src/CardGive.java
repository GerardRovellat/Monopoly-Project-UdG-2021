import java.util.ArrayList;

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
     * @param current_player jugador actiu
     */
    public void execute(ArrayList<Player> players,Player current_player) {
        System.out.println("Has de donar una propietats d'algun dels seus adversaris");
        if (current_player.haveFields()) {
            Player player = playerSelect(players,current_player);
            BoxField field = fieldSelect(current_player);
            player.addBox(field);
            current_player.removeBox(field);
            System.out.println("En " + current_player.getName() + " ha hagut de donar " + field.getName() + " a " + player.getName());
        }
        else System.out.println("No tens cap propietat i per tant, no en pots donar cap");
    }


    /**
     * @param players        ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player Jugador que ha de donar el terreny
     * @return el jugador selecionat o null si no n'hi ha cap
     * @brief Mostra tots els jugadors i deixa escollir a un d'ells a qui donar-li un terreny
     * @pre players size > 1
     * @post El jugador selecionat ha estat retornat ( mai retorna un jugador null per la precondicio)
     */
    private Player playerSelect(ArrayList<Player> players, Player current_player) {
        for (Player player : players) if (player != current_player) System.out.println(players.indexOf(player) + "- " + player.getName());
        System.out.println("Seleccioni el jugador");
        try {
            while (true) {
                int player_value = current_player.optionSelection("cardGivePlayerSelect", null, null, null, players, null, 0, null);
                if (player_value >= 0 && player_value < players.size() && player_value != players.indexOf(current_player)) return players.get(player_value);
                else System.out.println("Valor entrat incorrecte. Torni a provar");
            }
        } catch (NumberFormatException e) {System.out.println("Format incorrecte. Torna-hi.");}
        return null;
    }

    /**
     * @param current_player Jugador que ha de donar el terreny
     * @return el terreny selecionat per el jugador o null si no es pot
     * @brief Mostra tots els terreyns del jugador i deixa escollir a un d'ells
     * @pre current player fields > 0
     * @post El terreny selecionat ha estat retornat ( mai retorna un terreny null per la precondicio)
     */
    private BoxField fieldSelect(Player current_player) {
        if (current_player.getFields().size() > 0) {
            for (BoxField field : current_player.getFields()) {
                System.out.println(current_player.getFields().indexOf(field) + "- " + field.getName() + " (" + field.getPrice() + "€)");
            }
            System.out.println("Seleccioni un terreny");
            try {
                while (true) {
                    int field_value = current_player.optionSelection("cardGiveFieldSelect", null, null, null, null, null, 0, null);
                    if (field_value >= 0 && field_value < current_player.getFields().size())
                        return current_player.getFields().get(field_value);
                    else System.out.println("Valor entrat incorrecte. Torni a provar");
                }
            } catch (NumberFormatException e) { System.out.println("Format incorrecte. Torna-hi."); }
        }
        return null;
    }
    
    /**
     * @brief Sortida
     * @pre \p true
     * @post el missatge ha estat retornat
     * @return missatge de sortida
     */
    public String toString(){ return "Estàs obligat a donar inmediatament una de les teves propietats"; }

}

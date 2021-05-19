import java.util.ArrayList;

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
     * @param players           llista de jugadors de la partida
     * @param current_player    jugador actiu
     */
    public void execute(ArrayList<Player> players,Player current_player) {
        System.out.println("El jugador rebra una propietats d'algun dels seus adversaris");
        Player choosed = playerSelect(players, current_player);
        if (choosed != null) {
            BoxField field_choosed = fieldSelect(choosed);
            current_player.addBox(field_choosed);
            choosed.removeBox(field_choosed);
            System.out.println("En " + current_player.getName() + " ha adquirit " + field_choosed.getName());
        }
        else System.out.println("No hi ha cap jugador amb propietats per rebre");
    }

    /**
     * @param players        ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player Jugador que reb el terreny
     * @return el jugador selecionat o null si no n'hi ha cap
     * @brief Mostra tots els jugadors i deixa escollir a un d'ells
     * @pre players size > 1 i algun de ells != jugador actual
     * @post El jugador selecionat ha estat retornat i te com a minim un terreny o null si no hi ha cap jugador amb terrenys
     */
    private Player playerSelect(ArrayList<Player> players,Player current_player) {
        int active_players = 0;
        for (Player player : players){
            if (current_player != player && player.haveFields() ) {
                System.out.println(players.indexOf(player) + "- " + player.getName());
                active_players++;
            }
        }
        if (active_players>0) {
            try {
                while (true) {
                    System.out.println("Seleccioni el jugador");
                    int player_value = current_player.optionSelection("cardGetPlayerSelect", null, null, null, players, null, 0, null);
                    if (players.get(player_value) != current_player && players.get(player_value).haveFields()) return players.get(player_value);
                    else System.out.println("Error, sel·leccioni un jugador de la llista");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format incorrecte. Torna-hi.");
            }
        }
        return null;
    }

    /**
     * @param choosed     Jugador que ha de donar el terreny
     * @return el terreny selecionat per el jugador o null si no es pot
     * @brief Mostra tots els terreyns del jugador i deixa escollir a un d'ells
     * @pre choosed te algun terreny ( condicio heredada de playerSelect )
     * @post El terreny selecionat ha estat retornat, o en la seva absencia, s'ha retorntat un terreny null ( no retornarà mai null per la precondicio )
     */
    private BoxField fieldSelect(Player choosed) {
        for (BoxField field : choosed.getFields()) {
            System.out.println(choosed.getFields().indexOf(field) + "- " + field.getName() + " (" + field.getPrice() + ")");
        }
        try {
            while (true) {
                System.out.println("Seleccioni un terreny");
                int field_value = choosed.optionSelection("cardGetFieldSelect", null, null, null, null, null,0,null);
                if (field_value >= 0 && field_value < choosed.getFields().size()) return choosed.getFields().get(field_value);
                else System.out.println("Error, sel·leccioni un terreny correcte");
            }
        } catch (NumberFormatException e) {
            System.out.println("Format incorrecte. Torna-hi.");
        }
        return null;
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

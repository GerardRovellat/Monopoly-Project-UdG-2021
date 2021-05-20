import java.util.ArrayList;

/**
 * @author Marc Got
 * @file Box.java
 * @class Box
 * @brief Classe de Casella. Gestiona les dades i accions comunes entre tots els diferents tipus de casella
 */
public class Box {

    public int position;        ///< Posicio de la casella en el taulell
    public String type;         ///< Tipus de casella. ( Start, Field, Bet, Luck, DirectComand, Empty )
    private final String name;        ///< Nom de la casella

    /**
     * @brief Constructor de Box
     * @pre true
     * @post la casella ha estat creada
     * @param position Posicio de la casella
     * @param type Tipus de casella. ( Start, Field, Bet, Luck, DirectComand, Empty )
     * @param name Nom de la casella
     */
    public Box(int position,String type,String name) {
        this.position = position;
        this.type = type;
        this.name = name;
    }

    /**
     * @brief Getter de la posicio de la casella
     * @pre true
     * @post La posicio de la casella ha estat retornada
     * @return posicio de la casella en el taulell
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * @brief Getter del tipus de casella
     * @pre true
     * @post el tipus de casella ha estat retornat
     * @return tius de casella
     */
    public String getType() { return this.type; }

    /**
     * @brief Sortida per pantalla de la informació basica de la casella i, si n'hi ha, el jugador que està en aquesta
     * @pre true
     * @post La casella s'ha tret per pantalla
     * @param players llista de jugadors de la partida
     */
    public String print(ArrayList<Player> players) {
        String output_text ="";
        if (this.position > 9) {
            if (this.type.equals("FIELD")) output_text += this.position + " | " + this.type + ":   " + this.name + playerInPosition(players)+"\n";
            else output_text += this.position + " | " + this.name + playerInPosition(players)+"\n";
        }
        else {
            if (this.type.equals("FIELD")) output_text += this.position + "  | " + this.type + ":   " + this.name + playerInPosition(players)+"\n";
            else output_text += this.position + "  | " + this.name + playerInPosition(players)+"\n";
        }
        return output_text;
    }

    /**
     * @brief Busca tots els jugadors que estan en aquesta casella i crea un String amb ells
     * @pre true
     * @post El string amb el nom de tots els jugadors ha estat creat
     * @param players llista de jugadors de la partida
     * @return nom dels jugadors que estan en la casella
     */
    private String playerInPosition(ArrayList<Player> players) {
        StringBuilder playersInPosition = new StringBuilder();
        for(Player aux : players) if (aux.getPosition()==position) playersInPosition.append(" | ").append(aux.getName());
        return playersInPosition.toString();
    }


}

import java.util.ArrayList;
/**
 * @author Gerard Rovellat
 * @file optionalActions.java
 * @class optionalActions
 * @brief Interficie general d'accions opcionals del joc de Monopoly.
 */
public interface optionalActions {

    /**
     * @brief toString que tindra cada acciño opcional per tal de mostrar la seva funcio.
     * @pre \p true
     * @post La informació ha sigut mostrada per pantalla.
     * @return Informació especifica de cada acció opcional.
     */
    String toString();

    /**
     * @brief Execució d'una acció opcional, depenent de quina, farà una funcio o altre descrita en el seu fitxer
     * java corresponent.
     * @pre \p true
     * @post Retorna \p true si l'acció s'ha realitzat correctament, \p false altrament.
     * @return \p true si s'ha pogut realitzar l'acció, \p false altrament.
     */
    boolean execute(ArrayList<Player> players,Player current_player,Movement aux);
}
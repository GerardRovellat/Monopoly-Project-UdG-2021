import java.util.ArrayList;

/**
 * @author Gerard Rovellat
 * @file CardGo.java
 * @class CardGo
 * @brief Targeta que mou al jugador a una casella determinada en el tauler de Monopoly.
 */
public class CardGo extends Card{
    private int position;

    /**
     * @brief Constructor de CardGo.
     * @pre \p true
     * @post Crea una targeta CardGo amb les propietats entrades i heredades.
     * @param postposable \p true si la targeta es pot posposar i ser guardada pel jugador, \p false altrament.
     * @param position posició final on ha de moure's el jugador en cas d'executar-la.
     */
    public CardGo (boolean postposable,int position) {
        super("GO",postposable);
        this.position = position;
    }

    /**
     * @brief Mètode per executar la targeta CardGo.Moura el jugador fins a la posició que li indiqui la targeta.
     * @pre \p players != null, \p board != null, \p current_player != null, \p rewards != null
     * @post El jugador ha estat mogut fins a la posició \p position.
     * @param players ArrayList dels jugadors que estan jugant al Monopoly, no és usada en aquesta implementació
     *                però s'ha de passar.
     * @param board tauler de Monopoly en el qual s'esta jugant.
     * @param current_player jugador actual que executa la targeta CardGo.
     * @param rewards ArrayList de recompenses que pot donar-te la casella de sortida.
     */
    public void execute(ArrayList<Player> players,Board board, Player current_player, ArrayList<String> rewards) {
        System.out.println("Vas immediatament a la casella "+ position +" i si passes per la casella de sortida, cobra la recompensa");
        board.movePlayer(current_player,this.position,rewards);
    }

    /**
     * @brief toString per mostrar la descripció de la targeta CardGo per text.
     * @pre \p true
     * @post \p CardGo ha estat mostrada per pantalla.
     * @return String de la sortida per pantalla.
     */
    public String toString(){
        return "Vas immediatament a la casella "+ position +" i si passes per la casella de sortida, cobra la recompensa";
    }

}

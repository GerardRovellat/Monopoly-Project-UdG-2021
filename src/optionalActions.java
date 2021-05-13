import java.util.ArrayList;
/**
 * @author Gerard Rovellat
 * @file CardGo.java
 * @class CardGo
 * @brief Targeta que mou al jugador a una casella determinada en el tauler de Monopoly.
 */
public interface optionalActions {
// General actions interface
    public String toString();
    public boolean execute(ArrayList<Player> players,Player current_player,Movement aux);
}
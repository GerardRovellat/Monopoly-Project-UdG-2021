import java.util.ArrayList;

public interface optionalActions {
// General actions interface
    public String toString();
    public boolean execute(ArrayList<Player> players,Player current_player,Movement aux);
}
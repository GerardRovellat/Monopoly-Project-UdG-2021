import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

public class Board {
    private SortedMap<Integer,Box> board;
    private HashMap<String,Player> players;

    public Board () {}

    public void addPlayer(Player player) {
        players.put(player.getName(),player);
    }

    public void movePlayer (Player player, int position) {
        players.get(player.getName()).movePlayer(position);
    }

    public void addBox(Box box) {
        board.put(box.getPosition(),box);
    }
}

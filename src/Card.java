import java.util.ArrayList;

public class Card {
    private String type;
    private boolean postposable;
    private ArrayList<Player> players;
    private Board board;


    public Card(String type,boolean postposable,ArrayList<Player> players,Board board) {
        this.type = type;
        this.postposable = postposable;
        this.players = players;
        this.board = board;
    }

    public boolean isPostposable() {
        return this.postposable;
    }


}

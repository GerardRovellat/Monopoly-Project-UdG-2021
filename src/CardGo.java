import java.util.ArrayList;

public class CardGo extends Card{
    private int position;

    public CardGo (String type, boolean postposable, ArrayList<Player> players, Board board,int position) {
        super(type,postposable,players,board);
        this.position = position;
    }

    public void execute() {

    }
}

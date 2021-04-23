import java.util.ArrayList;

public class CardGo extends Card{
    private int position;

    public CardGo (String type, boolean postposable,int position) {
        super(type,postposable);
        this.position = position;
    }

    public void execute(ArrayList<Player> players, Board board,int position) {

    }
}

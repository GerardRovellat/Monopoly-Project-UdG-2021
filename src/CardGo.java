import java.util.ArrayList;

public class CardGo extends Card{
    private int position;

    public CardGo (boolean postposable,int position) {
        super("GO",postposable);
        this.position = position;
    }

    public void execute(ArrayList<Player> players,Board board, Player current_player, ArrayList<String> rewards) {
        System.out.println("Vas immediatament a la casella "+ position +" i si passes per la casella de sortida, cobra la recompensa");
        board.movePlayer(current_player,this.position,rewards);
    }

    public String toString(){
        return "Vas immediatament a la casella "+ position +" i si passes per la casella de sortida, cobra la recompensa";
    }

}

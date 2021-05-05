import java.util.ArrayList;

public class CardGo extends Card{
    private int position;

    public CardGo (String type, boolean postposable,int position) {
        super(type,postposable);
        this.position = position;
    }

    public void execute(ArrayList<Player> players,Board board, Player actual_player, ArrayList<String> rewards) {
        System.out.println("Vas immediatament a la casella "+ position +" i si passes per la casella de sortida, cobra la recompensa");

        board.movePlayer(actual_player,this.position,rewards);
        //FALTA MOURE JUGADOR A TAULER

    }
}

import java.util.ArrayList;

public class Loan implements optionalActions{

    private ArrayList<Player> players_list = new ArrayList<>();

    public Loan() {};

    @Override // Heretat d'Object
    public String toString() {

        return "DONE";
    }

    public boolean execute(ArrayList<Player> players,Player actual_player) {
        return false;
    }
}
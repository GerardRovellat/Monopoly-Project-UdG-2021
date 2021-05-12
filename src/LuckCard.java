import java.util.ArrayList;
import java.util.Scanner;

public class LuckCard implements optionalActions{


    private ArrayList<Player> players_list = new ArrayList<>();

    @Override // Heretat d'Object
    public String toString() {

        return "CARTA DE SORT: pot usar alguna carta de sort que hagi rebut amb anterioritat";
    }

    public boolean execute(ArrayList<Player> players,Player current_player, Movement aux) {
        Scanner scan = new Scanner(System.in);
        if (current_player.getLuckCards().isEmpty()) {
            System.out.println("Cap targeta sort en propietat");
        }
        else {
            int card_nr = 1;
            for (Card card : current_player.getLuckCards()) {
                System.out.println(card_nr + "- " + card);
                card_nr++;
            }
            System.out.println("Quina targeta vols utilitzar?");
            while (card_nr < 1 || card_nr > current_player.getLuckCards().size()) {
                System.out.println("Introduieixi una opcio valida");
                card_nr = scan.nextInt();
            }
            aux.runCard(current_player.getLuckCards().get(card_nr-1));
            aux.getCards().add(0,current_player.getLuckCards().get(card_nr-1));
        }
        return true;
    }
}
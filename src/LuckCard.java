import java.util.ArrayList;
import java.util.Scanner;

/**
 * @file Monopoly.java
 * @class Monopoly
 * @brief Classe que s'encarrega d'administrar l'inicialització, els torns i funcionament general
 * i la finalització del joc Monopoly.
 */
public class LuckCard implements optionalActions{

    private ArrayList<Player> players_list = new ArrayList<>();             ///< Llista de Jugadors

    /**
     * @brief Constructor per defecte de LuckCard.
     * @pre \p true
     * @post Crea una acció opcional LuckCard
     */
    public LuckCard() {}

    /**
     * @brief toString per mostrar la descripció de l'acció LuckCard per text.
     * @pre \p true
     * @post Mostra la descripció de LuckCard.
     * @return String de la sortida per pantalla de LuckCard.
     */
    @Override
    public String toString() {
        return "CARTA DE SORT: pot usar alguna carta de sort que hagi rebut amb anterioritat";
    }

    /**
     * @brief Mètode per executar el procés d'utilitzar una targeta sort en la seva propietat al final del torn.
     * @pre \p true
     * @post Crea Monopoly amb els atributs entrats.
     * @param players Llista de Jugadors jugant al Monopoly.
     * @param current_player Jugador actual del torn.
     * @param aux moviment que li passem ja que en aquest cas sera el mateix moviment que executarà una carta.
     * @return \p true quan acabi d'executar la carta de sort.
     */
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
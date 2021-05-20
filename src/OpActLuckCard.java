import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Gerard Rovellat
 * @file LuckCard.java
 * @class LuckCard
 * @brief Classe que s'encarrega d'administrar l'acció opcional de executar una targeta sort al final del torn
 * d'un Jugador.
 */
public class OpActLuckCard implements optionalActions {

    /**
     * @brief Constructor per defecte de LuckCard.
     * @pre \p true
     * @post Crea una acció opcional LuckCard
     */
    public OpActLuckCard() {}

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
            aux.getOutput().fileWrite(current_player.getName()+"Cap targeta sort en propietat");
            System.out.println("Cap targeta sort en propietat");
        }
        else {
            int card_nr = 1;
            for (Card card : current_player.getLuckCards()) {
                System.out.println(card_nr + "- " + card);
                card_nr++;
            }
            boolean valid = false;
            do {
                try {
                    System.out.println("Quina targeta vols utilitzar?");
                    card_nr = scan.nextInt();
                    if (card_nr < 1 || card_nr > current_player.getLuckCards().size()) throw new Exception();
                    valid = true;
                } catch (InputMismatchException e_format) {
                    scan.nextLine();
                    System.out.println("FORMAT ENTRAT INCORRECTE: Torna-hi...");
                } catch (Exception e_range){
                    System.out.println("OPCIO INCORRECTE: Torna-hi...");
                }
            }while (!valid);
            aux.runCard(current_player.getLuckCards().get(card_nr-1));
            aux.getOutput().fileWrite(current_player+"> S'ha utilitzat: "+current_player.getLuckCards().get(card_nr-1));
            aux.getCards().add(0,current_player.getLuckCards().get(card_nr-1));
            current_player.removeLuckCard(current_player.getLuckCards().get(card_nr-1));
        }
        return true;
    }
}
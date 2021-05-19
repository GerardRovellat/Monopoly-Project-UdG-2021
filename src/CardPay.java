import java.util.ArrayList;

/**
 * @author Marc Got
 * @file CardPay.java
 * @class CardPay
 * @brief Targeta que fa pagar la quantitat assignada a un jugador
 */

public class CardPay extends Card{
    private final int quantity;                                       ///< quantitat a pagar

    /**
     * @brief Constructor de CardPay
     * @pre \p true
     * @post La carta ha estat creada
     * @param postposable true si es posposable, false altrament
     * @param quantity quantitat a pagar
     */
    public CardPay ( boolean postposable, int quantity) {
        super("PAY",postposable);
        this.quantity = quantity;
    }

    /**
     * @brief Execucio de la carta
     * @pre \p true
     * @post la quantitat ha estat pagada a un altre jugador
     * @param players llista de jugadors de la partida
     * @param board taulell
     * @param current_player jugador actiu
     * @param aux Classe movement per poder cridar a accions opcionals
     */
    public void execute(ArrayList<Player> players, Board board, Player current_player, Movement aux) {
        System.out.println("Has de pagar "+quantity+" a un dels altres jugadors");
        if (current_player.getMoney() >= quantity) {
            int option_nr = 0;
            for (Player player : players) {
                if (current_player != player) {
                    System.out.println(option_nr + "- " + player.getName() + " (" + player.getMoney() + ")");
                }
                option_nr++;
            }
            System.out.println("A quin jugador vols pagar?");
            option_nr = current_player.optionSelection("cardPayPlayerSelect",null,null,null,players,null,0,null);
            while (option_nr < 0 || option_nr > players.size() - 1 || players.indexOf(current_player) == option_nr) { // POSIBLE ERROR AQUI
                System.out.println("Valor entrat erroni, torni a provar");
                option_nr = current_player.optionSelection("cardPayPlayerSelect",null,null,null,players,null,0,null);
            }
            current_player.pay(quantity);
            players.get(option_nr).charge(quantity);
            System.out.println("El jugador " + current_player.getName() + " ha pagat " + quantity + "€ a " + players.get(option_nr).getName());
        }
        else {
            System.out.println("No tens suficient diners per pagar");
            if (board.isBankrupt(current_player,quantity,aux)) {
                board.transferProperties(current_player,null,aux);
                current_player.goToBankruptcy();
            }
        }
    }

    /**
     * @brief Sortida
     * @pre \p true
     * @post el missatge ha estat retornat
     * @return missatge de sortida
     */
    public String toString(){
        return "Has de pagar "+quantity+" a un dels altres jugadors";
    }

}

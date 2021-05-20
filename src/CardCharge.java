import java.util.HashMap;
import java.util.Random;

/**
 * @author Marc Got
 * @file CardCharge.java
 * @class CardCharge
 * @brief Implementa la funcions de la carta de tipus cobrar
 */

public class CardCharge extends Card{
    private final int quantity;                                           ///< quantitat a pagar
    private String message;                                         ///< Missatge de la carta
    private final HashMap<Integer,String> messages = new HashMap<>();     ///< llista de missatges posibles

    /**
     * @brief Constructor de CardCharge
     * @pre \p true
     * @post La carta ha estat creada
     * @param postposable true si es posposable, false altrament
     * @param quantity quantitat a pagar
     */
    public CardCharge (boolean postposable, int quantity) {
        super("CHARGE",postposable);
        this.quantity = quantity;
        messages.put(0,"VENS EL TEU BITLLET DE TREN PER VALLADOLID EN UNA SUBHASTA PER INTERNET REP " + quantity + " €");
        messages.put(1,"¡HAS GUANYAT EL POT DE LA LOTERIA REB " + quantity + " €!");
        messages.put(2,"BENEFICI PER LA VENTA DE LES TEVES ACCIONS " + quantity + " €");
        messages.put(3,"LA TEVA COMPAÑIA D'INTERNET OBTE BENEFICIS REP " + quantity + " €");
        messages.put(4,"ELS TEUS COMPANYS T'ALQUILEN LA TEVA VILLA A HAWAI DURANT UNA SEMANA COBRA " + quantity + " €");
        messages.put(5,"GUANAYS UN CONCURS DE KARAOKE PER LA TELEVISIO COBRA " + quantity + "€ POR UN CONTRATO DISCOGRÁFICO");
        messages.put(6,"HEREDAS D'UN FAMILIRAR REP " + quantity + " €");
        messages.put(7,"COMPRAS UN SEGELL I EL VENS PER MES DEL DOBLE REPS " + quantity + " €");
        messages.put(8,"GUANYAS LA DEMANDA DEL PARTE DEL COTXE REPS " + quantity + " €");
        messages.put(9,"REPS " + quantity + " € DE BENEFICIS PER EL LLOGUER DEL TEU JET PRIVAT");
        messages.put(10,"DEVOLUCIÓ D'HISENDA REPS " + quantity + " €");
        messages.put(11,"HAS GUANYAT LA TEVA APOSTA DEL MADRID ELIMINAT DE CHAMPIONS REPS " + quantity + " €");
        Random rand = new Random();
        message = messages.get(rand.nextInt(messages.size()) - 1);
    }

    /**
     * @brief Execucio de la carta
     * @pre \p true
     * @post El jugador ha rebut la quantitat assignada
     * @param current_player jugador actiu
     */
    public void execute(Player current_player) {
        System.out.println(message);
        current_player.charge(quantity);
        System.out.println("DINERS REBUTS");
    }

    /**
     * @brief Getter de la quantitat a cobrar
     * @pre \p true
     * @post la quantitat a cobrar ha estat retornada
     * @return la quantitat a cobrar
     */
    public int getQuantity() { return this.quantity; }

    /**
     * @brief Sortida
     * @pre \p true
     * @post el missatge ha estat retornat
     * @return missatge de sortida
     */
    public String toString(){
        return message;
    }
}

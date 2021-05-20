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
        messages.put(0,"VENDES TU BILLETE DE TREN PARA VALLADOLID EN UNA SUBASTA POR INTERNET RECIBE " + quantity + " €");
        messages.put(1,"¡HAS GANADO EL BOTE DE LA LOTERÍA RECIBE " + quantity + " €");
        messages.put(2,"BENEFICIO POR LA VENTA DE SUS ACCIONES RECIBE " + quantity + " €");
        messages.put(3,"TU COMPAÑIA DE INTERNET OBTIENE BENEFICIOS RECIBE " + quantity + " €");
        messages.put(4,"TUS COMPAÑEROS TE ALQUILAS TU VILLA EN CANNES DURANTE UNA SEMANA COBRA " + quantity + " €");
        messages.put(5,"GANAS UN CONCURSO DE KARAOKE EN TELEVISIÓN COBRA " + quantity + "€ POR UN CONTRATO DISCOGRÁFICO");
        messages.put(6,"HEREDAS 3M € PERO LOS GRAVÁMENES SON MUY ALTOS RECIBE " + quantity + " €");
        messages.put(7,"COMPRAS UNA ACUARELA EN MELILLA Y LA VENDES POS MÁS DEL DOBLE RECIBE " + quantity + " €");
        messages.put(8,"GANAS LA DEMANDA DE TU SEGURO DE COCHE RECIBE " + quantity + " €");
        messages.put(9,"RECIBE " + quantity + " € DE BENEFICIOS POR ALQUILAR LOS SERVICIOS DE TU JET PRIVADO");
        messages.put(10,"DEVOLUCIÓN DE HACIENDA COBRA " + quantity + " €");
        messages.put(11,"HAS GANADO TU APUESTA EN LA ELIMINACIÓN DEL MADRID EN CHAMPIONS COBRA " + quantity + " €");
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

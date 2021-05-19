import java.util.HashMap;
import java.util.Random;

/**
 * @author Marc Got
 * @file CardFine.java
 * @class CardFine
 * @brief Implementa la funcions de la carta de tipus multa
 */

public class CardFine extends Card{
    private final int quantity;                                           ///< quantitat a pagar
    private String message;                                         ///< Missatge de la carta
    private final HashMap<Integer,String> messages = new HashMap<>();     ///< llista de missatges posibles

    /**
     * @brief Constructor de CardFine
     * @pre \p true
     * @post La carta ha estat creada
     * @param postposable true si es posposable, false altrament
     * @param quantity quantitat a pagar
     */
    public CardFine (boolean postposable, int quantity) {
        super("FINE",postposable);
        this.quantity = quantity;
        messages.put(0,"PAGA " + quantity + " € POR UN FIN DE SEMANA EN UN BALEARIO DE 5 ESTRELLAS");
        messages.put(1,"ASISTES A UNA FERIA BENÉFICA EN SEVILLA PAGA " + quantity + "€");
        messages.put(2,"PAGA " + quantity + " € POR INVITAR A TODOS TUS AMIGOS A UN VIAJE A SANTA CRUZ DE TENERIFE");
        messages.put(3,"PAGA UNA MULTA DE " + quantity + "€ POR SALTARTE EL TOQUE DE QUEDA");
        messages.put(4,"EL AUMENTO DEL IMPUESTO SOBRE TUS BIENES INMUEBLES AFECTA A TODAS TUS PROPIEDADES. PAGA " + quantity + "€ A LA BANCA");
        messages.put(5,"PIDES A FAMOSOS INTERIORISTAS QUE DECOREN TUS PROPIEDADES. PAGA " + quantity + "€ A LA BANCA");
        messages.put(6,"CONSTRUYES UNA PISCINA CUBIERTA EN TU APARTAMENTO DE MADRID. PAGA " + quantity + "€");
        messages.put(7,"PAGA  " + quantity + "€ POR LA MATRÍCULA DEL COLEGIO PRIVADO");
        messages.put(8,"TE MULTAN POR USAR EL MÓVIL MIENTRAS CONDUCES. PAGA " + quantity + "€");
        messages.put(9,"HAS REALIZADO UNA BARBACOA CON MÀS DE 6 PERSONAS. PAGA"  + quantity + "€ POR INCUMPLIMIENTO DE LAS RESTRICCIONES");
        messages.put(10,"TE HAN PILLADO YENDO AL SUPERMERCADO SIN MASCARILLA. PAGA " + quantity + "€ POR INCUMPLIMIENTO DE LAS RESTRICCIONES");
        messages.put(11,"HAS SUSPENDIDO LA ASIGNATURA DE PROYECTO DE PROGRAMACION. PAGA " + quantity + "PARA QUE EL PROFESOR TE APRUEVE");
    }

    /**
     * @brief Execucio de la carta
     * @pre \p true
     * @post La multa ha estat pagada
     * @param board taulell
     * @param current_player jugador actiu
     * @param aux Movement que crida Buy, en aquesta implementació, \p m no és usada però s'ha de passar.
     */
    public void execute(Board board,Player current_player, Movement aux) {
        Random rand = new Random();
        message = messages.get(rand.nextInt(messages.size()) - 1);
        System.out.println(message);
        for(boolean end = false;!end;) {
            if (current_player.getMoney() >= quantity) {
                current_player.pay(quantity);
                end = true;
                System.out.println("MULTA PAGADA");
            } else {
                System.out.println("No tens diners suficients per fer front a la multa");
                if (!board.isBankrupt(current_player,quantity,aux)) {
                    board.transferProperties(current_player,null,aux);
                    end = true;
                }
                else System.out.println("Has aconseguit els diners suficients");
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
        return message;
    }
}

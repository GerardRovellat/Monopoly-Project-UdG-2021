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
        messages.put(0,"PAGA " + quantity + " € PER UN CAP DE SETMANA A UN BALEARI DE 5 ESTRELLES");
        messages.put(1,"ASSISTEIX A UNA FIRA BENEFICA A SEVILLA I PAGUES " + quantity + "€");
        messages.put(2,"PAGA " + quantity + " € PER INVITAR A TOTS ELS TEUS AMICS A UN VIATGE A MALLORCA");
        messages.put(3,"PAGA UNA MULTA DE " + quantity + "€ PER SALTARTE EL TOC DE QUEDA");
        messages.put(4,"EL AUGMENT DE L'IMPOST SOBRE ELS BÉNS IMMOBLES AFECTA TOTES LES PROPIETATS. PAGA " + quantity + "€ A LA BANCA");
        messages.put(5,"DEMANES A FAMOSOS INTERIORISTES QUE DECORIN LA TEVA CASA. PAGA " + quantity + "€ A LA BANCA");
        messages.put(6,"CONSTRUEIXES UNA PISCINA COBERTA AL TEU APARTAMENT DE MADRID. PAGA " + quantity + "€");
        messages.put(7,"PAGA  " + quantity + "€ PER LA MATRICULA DE LA UDG");
        messages.put(8,"ET MULTEN PER UTILITZAR EL MOVIL MENTRE CONDUEIXES. PAGA " + quantity + "€");
        messages.put(9,"HAS REALITZAT UNA BARBACOA AMB MÉS DE 6 PERSONES. PAGA"  + quantity + "€ PER INCUMPLIMENT DE LES RESTRICCIONS");
        messages.put(10,"T'HAN ENXAMPAT ANANT AL SUPERMERCAT SENSE MASCARETA. PAGA " + quantity + "€ PER INCUMPLIMENT DE LES RESTRICCIONS");
        messages.put(11,"HAS SUSPES LA ASSIGNATURA DE PROJECTE DE PROGRAMACIO. PAGA " + quantity + "PERQUE EL PROFESSOR T'APROVI");
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
                if (board.isBankrupt(current_player,quantity,aux)) {
                    board.transferProperties(current_player,null,aux);
                    current_player.goToBankruptcy();
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

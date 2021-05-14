/**
 * @author Marc Got
 * @file Bet.java
 * @class Bet
 * @brief Classe de casella de aposta. En aquesta, es gestionarà les dades i els calculs corresponents
 */

public class Bet extends Box{

    private final double[] combinations = new double[]{0,0,0,1,3,6,10,15,21,26,30,33,35}; ///<  Contè les combinacions acomulades sobre un total de 12 valors ( 2 daus )

    /**
     * @brief Constructor de Bet
     * @pre type == "Bet"
     * @post Crea una Box de tipus aposta
     * @param position posició de la casella en el taulell
     * @param type tipus de casella == "Bet"
     */
    public Bet(int position, String type) {
        super(position, type, "APOSTA");
    }

    /**
     * @brief Calcula el resultat de una aposta
     * @pre quantity > 0 i bet > 3
     * @post Retorna el valor final de la aposta si es guanyadora, o -1 si es perdedora
     * @param quanity quantitat apostada per el jugador
     * @param bet aposta del jugador
     * @param dice_result resultat dels daus obtinguts
     */
    public int betResult(int quanity, int bet, int dice_result) {
        if (dice_result >= bet) {
            Double aux = quanity * (1 + 10 * combinations[bet] / 36 ) ; // combinations[bet] = combinacions posibles de que surti un valor inferior o igual a bet
            return aux.intValue();
        }
        else return -1;
    }
}

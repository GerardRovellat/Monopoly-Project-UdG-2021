
/**
 * @author Marc Got
 * @file Start.java
 * @class Start
 * @brief Casella de sortida en el tauler de Monopoly.
 */

public class BoxStart extends Box {

    /**
     * @brief Constructor de Start
     * @pre true
     * @post Crea una casella Start
     * @param position posicio de la casella en el tauler.
     * @param type tipus de casella a la que pertany.
     */
    public BoxStart(int position, String type) {
        super(position,type,"SORTIDA");
    }

    /**
     * @brief Retorna el tipus \type de casella a la que pertany.
     * @pre true
     * @post El tipus \p type de casella ha estat retornat.
     * @return String amb el \p type de casella.
     */
    public String getType() {
        return this.type;
    }

}

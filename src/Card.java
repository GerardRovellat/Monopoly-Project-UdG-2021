
/**
 * @author Gerard Rovellat
 * @file Card.java
 * @class Card
 * @brief Implementa la funcions base i comunes totes les cartes.
 */

public class Card {
    private final String type;                //< Tipus de carta: ( CHARGE, FINE, GET, GIVE, GO, PAY )
    private final boolean postposable;        ///< true si es posposable, false altrament

    /**
     * @brief Constructor de Card.
     * @pre \p true
     * @post La carta ha estat creada
     * @param type tipus de carta
     * @param postposable true si es posposable, false altrament
     */
    public Card(String type,boolean postposable) {
        this.type = type;
        this.postposable = postposable;
    }

    /**
     * @brief Comprova si la carta es posposable
     * @pre \p true
     * @post s'ha comprovat si la carta es posposable
     * @return treu si la carta es posposable, false altrament
     */
    public boolean isPostposable() {
        return this.postposable;
    }

    /**
     * @brief Retorna el tipus de la carta
     * @pre \p true
     * @post el tipus de la carta s'ha retornat
     * @return el tipus de la carta
     */
    public String getType() {
        return this.type;
    }

}

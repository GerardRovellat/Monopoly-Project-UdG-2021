/**
 * @author Gerard Rovellat
 * @file Empty.java
 * @class Empty
 * @brief Casella buida en el Monopoly, aquesta casella la anomanem "Buida" ja que no fem cap acció quan jugador hi cau
 * a sobre.
 */
public class BoxEmpty extends Box{

    /**
     * @brief Constructor de Empty.
     * @pre \p true
     * @post Crea una casella Empty amb les propietats d'entrada i heredades.
     * @param position posició en el tauler de la casella.
     * @param type tipus de casella a la que pertany.
     */
    public BoxEmpty(int position, String type) {
        super(position, type, "EMPTY");
    }
}
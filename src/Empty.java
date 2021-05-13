/**
 * @class Empty
 * @brief Casella buida en el Monopoly, aquesta casella la anomanem "Buida" ja que no fem cap acció quan jugador hi cau
 * a sobre.
 */
public class Empty extends Box{

    private String name;

    /**
     * @brief Constructor de Empty.
     * @pre \p true
     * @post Crea una casella Empty amb les propietats d'entrada i heredades.
     * @param position posició en el tauler de la casella.
     * @param type tipus de casella a la que pertany.
     * @param name nom de la casella.
     */
    public Empty(int position, String type, String name) {
        super(position, type, "BUIDA");
        this.name = name;
    }
}
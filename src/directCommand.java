/**
 * @author Gerard Rovellat
 * @file directCommand.java
 * @class directCommand
 * @brief Classe que administra les caselles de comanda directe \p directCommand. Aquesta classe tindra una targeta
 * perque quan s'hagi d'executar la casella la tractarem com si toques una targeta (les possibles accions son iguals.
 */
public class directCommand extends Box{

    private Card function;                              ///< Targeta a la que equival aquesta casella de directCommand

    /**
     * @brief Constructor de Buy.
     * @pre \p position > 0 i \p function != null
     * @post Crea una casella de comanda directe amb la funcio \p function.
     */
    public directCommand(int position, String type, Card function){
        super(position,type,function.getType());
        this.function = function;
    }

    /**
     * @brief Getter de funcio equivalent que te una targeta.
     * @pre \p true
     * @post La targeta ha sigut retornada.
     * @return targeta amb una funció \p function determinada.
     */
    public Card getCard() { return this.function; }
}

/**
 * @author Marc Got
 * @file PlayerLoan.java
 * @class PlayerLoan
 * @brief Classe que administra els préstecs entre prestatari i prestador.
 */
public class PlayerLoan {

    Player loaner;          ///< Jugador prestador del prestéc.
    Player loaned;          ///< Jugador prestatari del prestéc.
    int value;              ///< Valor del préstec
    int interest;           ///< Interes del préstec
    int turns;              ///< Torns a retornar el préstec

    /**
     * @brief Constructor de \p PlayerLoan.
     * @pre \p loaner != null i \p loaned != null
     * @post S'ha creat un préstec amb els atributs entrats.
     */
    public PlayerLoan(Player loaner,Player loaned,int value,int interest,int turns) {
        this.loaner = loaner;
        this.loaned = loaned;
        this.value = value;
        this.interest = interest;
        this.turns = turns;
    }

    /**
     * @brief Resta un torn per haver de tornar el préstec
     * @pre \p true
     * @post Torn restat a \p turns.
     */
    public void nextTurn() {
        turns=turns-1;
    }

    /**
     * @brief Calcula el valor que se l'hi haura de tornar al prestador.
     * @pre \p true
     * @post Retorne el valor a tornar al prestador.
     * @return valor a retornar \p result.
     */
    public int returnValue() {
        Double result = value * ( 1 + (double) interest / 100 );
        return result.intValue();
    }

    /**
     * @brief Consultor per saber si s'ha acabat els torns del préstec
     * @pre \p true
     * @post Retorna \p true si numero de torns finalitzat, \p false altrament
     * @return \p true si \p turns = 0, \p false altrament.
     */
    public boolean isEnd() {
        return turns == 0;
    }

    /**
     * @brief Acció per pagar el préstec del \p loaned al \p loaner.
     * @pre \p true
     * @post Retorna \p true si s'ha pagat el préstec, \p false altrament.
     * @return \p true si s'ha pagat \p false altrament.
     */
    public boolean payLoan() {
        boolean aux = false;
        if (isEnd()) {
            System.out.println("El prestec ha acabat. INFO DEL PRESTEC:");
            System.out.println(this.toString());
            loaned.pay(returnValue());
            loaner.charge(returnValue());
            aux = true;
        }
        return aux;
    }

    /**
     * @brief Sortida resumida dels préstecs actius de \p loaned.
     * @pre \p true
     * @post S'ha mostrat els préstec que queden actius del jugador \p loaned.
     * @return string amb la informació del préstec.
     */
    public String smallPrint() {
        return value + "€ en " + turns + " torns a " + loaner.getName() + " i un interes del " + interest + "%";
    }

    /**
     * @brief Sortida resumida dels préstecs actius de \p loaned.
     * @pre \p true
     * @post S'ha mostrat els préstec que queden actius del jugador \p loaned.
     * @return string amb la informació del préstec.
     */
    public Player getLoaner() {
        return loaner;
    }

    /**
     * @brief toString per mostrar la descripció dels préstecs PlayerLoan per text.
     * @pre \p true
     * @post La descripció dels préstecs ha estat mostrada.
     * @return Stirng buida.
     */
    @Override
    public String toString() {
        System.out.println("Propietari: " + loaner.getName());
        System.out.println("Valor: " + value);
        System.out.println("Interes: " + interest);
        System.out.println("Torns: " + turns);
        System.out.println("A retornar: " + returnValue());
        return "";
    }

}

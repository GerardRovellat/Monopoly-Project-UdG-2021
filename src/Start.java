
/**
 * @file Start.java
 * @class Start
 * @brief Casella de sortida en el tauler de Monopoly.
 */

public class Start extends Box{

    private Field field_reward; ///< @brief recompensa en forma de terreny*/
    private int money_reward; ///< @brief recompensa economica*/

    /**
     * @brief Constructor de Start
     * @pre true
     * @post Crea una casella Start
     * @param position posicio de la casella en el tauler.
     * @param type tipus de casella a la que pertany.
     */
    public Start(int position, String type) {
        super(position,type,"SORTIDA");
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Sets the property that is given as a reward
     */
    public void setFieldReward(Field field_reward) {
        this.field_reward = field_reward;
    }

    /**
     * @brief Fixa els diners que \p Start dona com a recompensa.
     * @pre true
     * @post La quantitat de diners ha estat fixada.
     * @param money_reward quantitat de diners a fixar coma recompensa.
     */
    public void setMoneyReward(int money_reward) {
        this.money_reward = money_reward;
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

    /**
     * @brief Retorna el terreny \p Field que dona com a recompensa
     * @pre true
     * @post El terreny ha estat retornat.
     * @return \p Field que es dona com a recompensa.
     */
    public Field fieldReward(){
        return this.field_reward;
    }

    /**
     * @brief Retorna la quantitats de diners que la casella Start et dona de recompensa per passar-hi.
     * @pre \p true
     * @post La quantitat de diners \p money_reward ha estat retornada.
     * @return int de la quantitat de diners que te Start com a recompensa.
     */
    public int moneyReward() {
        return this.money_reward;
    }

}

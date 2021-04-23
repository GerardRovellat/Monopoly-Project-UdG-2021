public class Start extends Box{

    private String type; // field,money,both
    private Field field_reward;
    private int money_reward;

    /**
     * @brief $$$$
     * @pre true
     * @post Create a start box
     */
    public Start(int position,String reward_type) {
        super(position);
        this.type = reward_type;
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
     * @brief $$$$
     * @pre true
     * @post Sets the the amount of money that is given as a reward
     */
    public void setMoneyReward(int money_reward) {
        this.money_reward = money_reward;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Gets the type of the reward that this start box gives (type = property / type = money)
     */
    public String getType() {
        return this.type;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the property that is given as a reward
     */
    public Field fieldReward(){
        return this.field_reward;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the amount of money that is given as a reward
     */
    public int moneyReward() {
        return this.money_reward;
    }

}

import java.util.*;

public class Player {
    private String name;
    private int money;
    private ArrayList<Field> boxes_in_property = new ArrayList<>();
    private int position;
    private ArrayList<Card> luckCards = new ArrayList<>();
    private boolean bankruptcy = false;


    /**
     * @brief $$$$
     * @pre true
     * @post Creates a Player with the input attributes
     */
    public Player(String name,int initial_money,int initial_position) {
        this.name = name;
        this.money = initial_money;
        this.position = initial_position;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Subtract money paid to another player or banker
     */
    public void pay(int amount){
        this.money = this.money - amount;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Add money paid to another player or banker
     */
    public void charge(int amount){
        this.money = this.money + amount;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns a list of luck cards that player have
     */
    public List<Card> getLuckCards() {
        return this.luckCards;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Add luck card to the player luck cards
     */
    public void addLuckCard(Card card){
        this.luckCards.add(card);
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Remove luck card to the player luck cards
     */
    public void removeLuckCard(Card card) {
        this.luckCards.remove(card);
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public String getName() {
        return name;
    }


    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public int getPosition() {
        return position;
    }


    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public int getMoney() {
        return money;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public int isAffordable(int price) {
        return money % price;
    }


    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public void movePlayer(int position) {
        this.position = this.position + position;
    }


    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public void addBox(Field box) {
        boxes_in_property.add(box);
    }


    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public void removeBox(Box box) {
        boxes_in_property.remove(box);
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public ArrayList<Field> getFields() {
        return this.boxes_in_property;
    }


    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public void goToBankruptcy() {
        bankruptcy = true;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$
     */
    public Boolean getBankruptcy() {
        return this.bankruptcy;
    }

    @Override
    public String toString() {
        System.out.println("INFORMACIÓ DEL JUGADOR: " + this.name);
        System.out.println("DINERS ENS CAIXA: " + this.money);
        System.out.println("PROPIETATS:");
        for(Field actual : boxes_in_property) {
            System.out.println("    " + actual.getName() + ". amb un valor de " + actual.getPrice() + "€");
        }
        if (!bankruptcy) System.out.println("JUGADOR ACTIU");
        else System.out.println("JUGADOR INACTIU ( EN FALLIDA )");
        return "\n";
    }
}





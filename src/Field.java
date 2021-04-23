import java.util.ArrayList;

public class Field extends Box{

    private int position;
    private String name;
    private int price;
    private String group;
    private int basic_rent;
    private int group_rent;
    private boolean buildable;
    private int max_buildings;
    private int builing_price;
    private boolean hotel;
    private int hotel_price;
    private ArrayList<Integer> buildings_rent;
    private int hotel_rent;

    /**
     * @brief $$$$
     * @pre true
     * @post Creates a Property with the input attributes
     */
    public Field(int position,String name,int price,String group,int basic_rent,int group_rent,boolean buildable,int max_buildings,int builing_price,boolean hotel,int hotel_price,ArrayList<Integer> buildings_rent,int hotel_rent) {
        this.position = position;
        this.name = name;
        this.price = price;
        this.group = group;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Change state of a property
     */
    public void changeBoughtState() {

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns price of property
     */
    public int getPrice(){

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns rent of the property
     */
    public int getRent() {

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns owner of the property
     */
    public Player getOwner() {

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Build one house on the property
     */
    public void build() {

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns TRUE if the property its buildable FALES otherwise
     */
    public boolean buildable() {

    }

}
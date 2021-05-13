import java.util.ArrayList;

/**
 * @author Marc Got
 * @file Field.java
 * @class Field
 * @brief Classe que administra els préstecs entre Jugadors del Monopoly.
 */
public class Field extends Box{

    private String name;                            ///< Nom del terreny.
    private int price;                              ///< Preu del terreny.
    private String group;                           ///< Color d'agrupació.
    private int basic_rent;                         ///< Preu lloguer sense agrupació.
    private int group_rent;                         ///< Preu lloguer agrupació.
    private String buildable;                       ///< Tipus de construcció.
    private int max_buildings;                      ///< Nombre màxim d' apartaments construibles.
    private int building_price;                     ///< Preu de cada apartament.
    private boolean hotel;                          ///< Es pot contruir hotel, true si, false no.
    private int hotel_price;                        ///< Preu de l'hotel.
    private ArrayList<Integer> buildings_rent;      ///< Lloguer depenent de les construccions.
    private int hotel_rent;                         ///< Lloguer del hotel.
    private Player owner;                           ///< Jugador propietari.
    private int builded = 0;                        ///< Nombre de edificacions construides.
    private boolean bought = false;                 ///< Estat de comprat o no.

    /**
     * @brief Constructor de terreny \p Field.
     * @pre \p true
     * @post Crea un terreny amb tots els atributs entrats.
     */
    public Field(int position,String type,String name,int price,String group,int basic_rent,int group_rent,String buildable,int max_buildings,int building_price,boolean hotel,int hotel_price,ArrayList<Integer> buildings_rent,int hotel_rent) {
        super(position,type,name);
        this.name = name;
        this.price = price;
        this.group = group;
        this.basic_rent = basic_rent;
        this.group_rent = group_rent;
        this.buildable = buildable;
        this.max_buildings = max_buildings;
        this.building_price = building_price;
        this.hotel = hotel;
        this.hotel_price = hotel_price;
        this.buildings_rent = buildings_rent;
        this.hotel_rent = hotel_rent;
        if (hotel) this.max_buildings++;
    }

    /**
     * @brief
     * @pre true
     * @post Add player as owner and change state of field to true
     */
    public void buy(Player owner) {
        this.owner = owner;
        this.bought = true;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Remove player as owner and change state of field to false
     */
    public void sell() {
        this.owner = null;
        this.bought = false;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns price of property
     */
    public int getPrice(){
        return this.price;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns rent of the property
     */
    public int getRent() {
        /*if (bought) {
            if (!hotel) return buildings_rent.get(2);
            else return hotel_rent;
        }
        else return 0;*/

        if (builded == 0) {
            return this.basic_rent;
        }
        else if (builded < max_buildings) {
            return this.buildings_rent.get(builded);
        }
        else if (builded == max_buildings) {
            return this.hotel_rent;
        }
        else return 0;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns owner of the property
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * @brief $$$$
     * @pre Es necesari saber que el usuari ja ha pagat el preu corresponent al apartament o hotel segons toqui i que es posible construir
     * @post Build one house on the property
     */
    public void build(int quanity) {
        builded = builded + quanity;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns TRUE if the property its buildable FALES otherwise
     */
    public boolean houseBuildable() {
        return builded < max_buildings;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns TRUE if the property its buildable FALES otherwise
     */
    public boolean hotelBuildable() {
        if (hotel) return builded == max_buildings;
        else return false;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$$
     */
    public int priceToBuild() {
        if (houseBuildable()) return building_price;
        else if (hotelBuildable()) return hotel_price;
        else return -1;
    }

    /**
     * @brief $$$$
     * @pre houseBuildable = true
     * @post $$$$$$$
     */
    public int numberOfHouseBuildable() {
        return max_buildings - builded;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$$
     */
    public boolean isBought() {
        return this.bought;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$$
     */
    public String getName() {
        return this.name;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$$
     */
    public int getNumberOfApartaments() {
        if (hotelBuildable()) return max_buildings;
        else return builded;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$$
     */
    public int getNumberOfHotels() {
        if (builded == max_buildings + 1) return 1;
        else return 0;
    }


    @Override
    public String toString() {
        System.out.println("\tTÍTUL DE PROPIETAT:                  " + this.name );
        System.out.println("\tPREU DE LA PROPIETAT:                " + this.price);
        System.out.println("\tLLOGUERS - Solar sense edificar      " + this.basic_rent + "€");
        int pos = 1;
        for (Integer current : buildings_rent) {
            System.out.println("\t           Amb " + pos + " apartaments:        " + current + "€");
            pos++;
        }
        System.out.println("\tAmb un hotel:                        " + this.hotel_rent + "€");
        System.out.println("\tPreu de cada Apartament:             " + this.building_price + "€");
        System.out.println("\tPreu de cada Hotel:                  " + this.hotel_price + "€ a mes de " + this.max_buildings + "apartaments");
        System.out.println("\tGrup de propietats:                  " + this.group);
        return "\n";
    }
}
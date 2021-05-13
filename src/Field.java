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
     * @brief Assigna un Jugador \p Player com a propietari \p owner i actualitza estat del terreny.
     * @pre \p owner != null
     * @post Propietari assignat i estat \p bought = true.
     */
    public void buy(Player owner) {
        this.owner = owner;
        this.bought = true;
    }

    /**
     * @brief Elimina el propietari \p owner del terreny.
     * @pre true
     * @post Elimina le Jugador com a propietari \p owner i canvia el estat \p bought a \p false.
     */
    public void sell() {
        this.owner = null;
        this.bought = false;
    }

    /**
     * @brief Getter del preu del terreny \p Field.
     * @pre true
     * @post Retorna el preu del terreny \p Field.
     * @return preu \p price del terreny.
     */
    public int getPrice(){
        return this.price;
    }

    /**
     * @brief Getter del lloguer del terreny \p Field depenent de les edificacions que tingui.
     * @pre true
     * @post Retorna el lloguer del terreny \p Field que correspon.
     * @return lloguer del terreny \p Field depenent de \p builded.
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
     * @brief Getter del propietari \p owner del terreny.
     * @pre true
     * @post Retorna el propietari \p owner del terreny \p Field.
     * @return Jugador \p owner propietari de \p Field.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * @brief Afegeix \p quantity com a edificis edificats al terreny \p Field
     * @pre Es necesari saber que el usuari ja ha pagat el preu corresponent al apartament o hotel segons toqui i que
     * posible construir
     * @post S'ha construit \p quantity apartaments al terreny.
     */
    public void build(int quantity) {
        builded = builded + quantity;
    }

    /**
     * @brief Consulta si es pot construïr cases en el terreny
     * @pre true
     * @post Retorna \p true si a la propietat s'hi pot construir cases, \p false altrament.
     * @return \p true si es pot construïr cases, \p false altrament
     */
    public boolean houseBuildable() {
        return builded < max_buildings;
    }

    /**
     * @brief Consulta si es pot contruïr un hotel a terreny.
     * @pre true
     * @post Retorna \p true si es pot construir un hotel a la propietat, \p false altrament,
     * @return \p true si es pot construïr un hotel, \p false altrament.
     */
    public boolean hotelBuildable() {
        if (hotel) return builded == max_buildings;
        else return false;
    }

    /**
     * @brief Consulta el preu per construïr el el edifici que correspon.
     * @pre true
     * @post Retorna el preu d'una casa \p building_price si pot ser construïda, \p hotel_price si nomes pot
     * construïr-se un hotel, -1 altrament.
     */
    public int priceToBuild() {
        if (houseBuildable()) return building_price;
        else if (hotelBuildable()) return hotel_price;
        else return -1;
    }

    /**
     * @brief Consulta el nombre de cases que es pot construïr en un terreny.
     * @pre houseBuildable = true
     * @post Retorna el numero de cases possibles a construïr.
     * @return numero de cases per construïr.
     */
    public int numberOfHouseBuildable() {
        return max_buildings - builded;
    }

    /**
     * @brief Consulta si la propietat esta comprada.
     * @pre true
     * @post Retorna \p true si la propietat esta comprada, \p false altrament.
     * @return \p true si \p bought = true, \p false altrament.
     */
    public boolean isBought() {
        return this.bought;
    }

    /**
     * @brief Getter del nom de la terreny.
     * @pre true
     * @post Retorna el nom del terreny.
     * @return string del nom \p name del terreny
     */
    public String getName() {
        return this.name;
    }

    /**
     * @brief Getter de nombre d'edificacions construïdes.
     * @pre true
     * @post Retorna el nombre d'apartaments construïts.
     * @return nombre de edificacions a \p Field.
     */
    public int getNumberOfApartaments() {
        if (hotelBuildable()) return max_buildings;
        else return builded;
    }

    /**
     * @brief Consulta el nombre d'hotels que estan construïts.
     * @pre true
     * @post Retorna el nombre d'hotels construïts.
     * @return nombre d'hotels a \p Field.
     */
    public int getNumberOfHotels() {
        if (builded == max_buildings + 1) return 1;
        else return 0;
    }

    /**
     * @brief toString per mostrar la descripció del terreny \p Field en text.
     * @pre true
     * @post Mostra els detalls de \p Field.
     * @return salt de linea.
     */
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
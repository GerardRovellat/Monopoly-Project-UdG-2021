import java.util.*;

/**
 * @author Marc Got
 * @file Player.java
 * @class Player
 * @brief Classe de Jugador del Monopoly. En aquesta classe hi haura tota la informació del jugador i algunes accions
 * que pot fer el jugador en si.
 */

public abstract class Player {
    private final String name;                                                ///< Nom del Jugador.
    private int money;                                                        ///< Diners del Jugador.
    private final ArrayList<BoxField> boxes_in_property = new ArrayList<>();     ///< Llista terrenys en propietat del Jugador.
    private int position;                                                     ///< Posició del Jugador
    private ArrayList<Card> luckCards = new ArrayList<>();                    ///< Llista targetes sort.
    private boolean bankruptcy = false;                                       ///< Estat fallida jugador.
    private ArrayList<PlayerLoan> loans = new ArrayList<>();                  ///< Llista de préstecs del Jugador.
    private final String type;                                                ///< Tipus de jugador ( CPU, USER )


    /**
     * @brief Constructor de Player.
     * @pre true
     * @post Crea un jugador amb els atributs entrats.
     * @param name nom del Jugador.
     * @param initial_money quantitat de diners inicials dels que disposa un Jugador.
     * @param initial_position posició inicial del Jugador.
     */
    public Player(String name,int initial_money,int initial_position,String type) {
        this.name = name;
        this.money = initial_money;
        this.position = initial_position;
        this.type = type;
    }

    /**
     * @brief El Jugador paga una quantitat de diners \p amount determinada.
     * @pre \p amount >= 0
     * @post La quantitat entrada \p amount ha estat restada de \p money del Jugador.
     * @param amount quantitat a pagar
     */
    public void pay(int amount){
        this.money = this.money - amount;
    }

    /**
     * @brief El jugador cobra una quantitat de diners \p amount determinada.
     * @pre \p amount >= 0
     * @post La quantitat entrada \p amount ha estat sumada de \p money del Jugador.
     * @param amount quantitat a cobrar
     */
    public void charge(int amount){
        this.money = this.money + amount;
    }

    /**
     * @brief Retorna en una llista les targetes sort \p luckCards del Jugador.
     * @pre true
     * @post \p luckCards ha estat retornada.
     * @return List de targetes sort del Jugador.
     */
    public List<Card> getLuckCards() {
        return this.luckCards;
    }

    /**
     * @brief Afegeix una targeta sort \p card a la propietat del Jugador.
     * @pre \p card != null
     * @post La targeta \p card ha estat afegida a la llista de targetes sort del Jugador.
     * @param card targeta que es vol afegir a les targetes de sort \p luckCards del Jugador.
     */
    public void addLuckCard(Card card){
        this.luckCards.add(card);
    }

    /**
     * @brief Elimina una targeta sort \p card de la propietat del Jugador.
     * @pre \p card != null
     * @post La targeta \p card ha estat eliminada de la llista de targetes sort del Jugador.
     * @param card targeta que es vol eliminar de les targetes de sort \p luckCards del Jugador.
     */
    public void removeLuckCard(Card card) {
        this.luckCards.remove(card);
    }

    /**
     * @brief Retorna el nom del Jugador el qual la crida.
     * @pre true
     * @post El nom del Jugador \p name ha estat tornat.
     * @return String que contindrà el nom \p name del Jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Retorna el tipus de Jugador el qual la crida.
     * @pre true
     * @post El tipus del Jugador \p type ha estat tornat.
     * @return String que contindrà el tipus \p type del Jugador.
     */
    public String getType() {
        return type;
    }


    /**
     * @brief Retorna la posició del Jugador el qual la crida.
     * @pre true
     * @post la posició del Jugador \p position ha estat tornada.
     * @return int que contindrà la \p position del Jugador.
     */
    public int getPosition() {
        return position;
    }


    /**
     * @brief Retorna els diners que té el Jugador en possessió.
     * @pre true
     * @post Els diners \p money del Jugador  han estat retornats.
     * @return int que contindrà els diners \p money de Jugador.
     */
    public int getMoney() {
        return money;
    }

    /**
     * @brief Calcula el nombre de apartaments que es pot permetre costruir al preu entrat
     * @pre price > 0
     * @post el nombre de apartaments construïbles ha estat retornada
     * @param price preu de un apartament
     * @return int que contindrà el nombre de apartaments construibles
     */
    public int numberOfBuildingsAffordable(int price) {
        return money / price;
    }


    /**
     * @brief Mou el Jugador a una posició \p position final en el tauler.
     * @pre \p postion > 0
     * @post El Jugador ha sigut mogut a \p position del tauler.
     * @param position posició final a la qual el Jugador s'ha de moure.
     */
    public void movePlayer(int position) {
        this.position = position;
    }


    /**
     * @brief Afegeix un terreny a la propietat d'un Jugador
     * @pre \p box != null
     * @post \p box ha estat afegit a la llista de propietats \p boxes_in_property de Jugador.
     * @param box terreny que serà afegit a la propietat de Jugador.
     */
    public void addBox(BoxField box) {
        if (box != null) {
            boxes_in_property.add(box);
            box.buy(this);
        }
    }

    /**
     * @brief Elimina un terreny a la propietat d'un Jugador
     * @pre \p box != null
     * @post \p box ha estat eliminat de la llista de propietats \p boxes_in_property de Jugador.
     * @param box terreny que serà eliminat de la propietat de Jugador.
     */
    public void removeBox(Box box) {
        boxes_in_property.remove(box);
    }

    /**
     * @brief Retorna la llista \p boxes_in_property de terrenys que te en propietat el Jugador.
     * @pre true
     * @post La llista \p boces_in_property ha estat retornada.
     * @return ArrayList que contindrà tots els terrenys en propeitat del Jugador.
     */
    public ArrayList<BoxField> getFields() {
        return this.boxes_in_property;
    }


    /**
     * @brief Declara el Jugador en fallida.
     * @pre true
     * @post El jugador ha estat declarat en fallida.
     */
    public void goToBankruptcy() {
        bankruptcy = true;
    }

    /**
     * @brief Retorna si el Jugador te terrenys en propietat o no.
     * @pre true
     * @post És retorna \p true si Jugador te terrenys, \p false altrament.
     * @return \p true si \p boxes_in_property no es buida, \p false altrament.
     */
    public boolean haveFields(){
        return !boxes_in_property.isEmpty();
    }

    public ArrayList<PlayerLoan> getLoans(){
        return loans;
    }

    /**
     * @brief Retorna si el Jugador es en fallida o no.
     * @pre true
     * @post És retorna \p true si Jugador es en fallida, \p false altrament.
     * @return \p true si \p bakruptcy es \p true, \p false altrament.
     */
    public Boolean getBankruptcy() {
        return this.bankruptcy;
    }

    /**
     * @brief Afegeix un préstec a la llista de préstecs del Jugador
     * @pre \p loaner != null, \p torns > 0
     * @post El préstec ha estat afegit a la llista de préstecs \p loans de Jugador.
     * @param loaner Jugador que ha prestat diners a Jugador.
     * @param value valor a retornar a \p loaner.
     * @param interest interes del préstec.
     * @param torns numero de torns el qual s'ha de tornar el préstec del Jugador.
     */
    public void addLoan(Player loaner, int value, int interest, int torns) {
        loans.add(new PlayerLoan(loaner,this,value,interest,torns));
    }


    /**
     * @brief Paga els préstecs que te pendents els Jugadors i gestiona els torns dels que encara no s'han de pagar
     * @pre true
     * @post els préstecs que han finalitzat s'han retornat i gestionat els torns dels que no
     */
    public void payLoans(Board board, Movement movement) {
        for (int i=0;i<loans.size();i++){
            PlayerLoan aux = loans.get(i);
            aux.nextTurn();
            if (aux.isEnd()) {
                boolean ableToPay = true;
                if (aux.returnValue() > money) ableToPay = board.isBankrupt(this, aux.returnValue(), movement);
                if (ableToPay) {
                    if (aux.payLoan()) loans.remove(aux);
                }
                else board.transferProperties(this,aux.getLoaner(),movement);
            }
        }
    }

    /**
     * @brief calcula el numero de terrenys de la agrupacio passada que te en propietat el jugador
     * @pre true
     * @post el numero de terrenys de la agrupacio passada s'ha retornat
     * @param group_name nom de la agrupacio
     * @return numero de terrenys de la agrupacio passada
     */
    public int numberOfAgrupationField(String group_name) {
        int cont = 0;
        for (BoxField aux : boxes_in_property) {
            if (aux.getGroup().equals(group_name)) cont++;
        }
        return cont;
    }

    /**
     * @brief toString per mostrar l'informació de Player per text.
     * @pre \p true
     * @post Player ha estat mostrat per pantalla amb tota la seva informació per poder seguir la partida adequadament.
     * @return String buida.
     */
    @Override
    public String toString() {
        StringBuilder output_text = new StringBuilder();
        output_text.append(this.name).append(": ").append(this.money).append("€\n");
        if (boxes_in_property.size() > 0) {
            output_text.append("PROPIETATS:\n");
            for (BoxField current : boxes_in_property) {
                output_text.append("    ").append(current.getName()).append(". amb un valor de ").append(current.getPrice()).append("€\n");
            }
        }
        else output_text.append("NO TE PROPIETATS\n");
        if (loans.size() > 0) {
            output_text.append("PRESTECS:\n");
            for (PlayerLoan loan : loans) {
                output_text.append("    ").append(loan.smallPrint()).append("\n");
            }
        }
        else output_text.append("NO TE PRESTECS\n");
        return output_text.toString();
    }

    public abstract int optionSelection(String type, Player player, BoxField field, ArrayList<Integer> options, ArrayList<Player> players, Card card, int value, ArrayList<optionalActions> optional_actions);

    public abstract String stringValueSelection(String type, Player player, BoxField field, int value, int second_value);

}





import java.util.*;

/**
 * @author Gerard Rovellat
 * @file Board.java
 * @class Board
 * @brief Classe del taulell. Gestiona la informació del taulell, les accions de moviment dels jugadors i altres com la gestio de la fallida
 */

public class Board {
    private int boxes_nr;                                           ///< numero de caselles dels taulell
    private SortedMap<Integer,Box> board = new TreeMap<>();         ///< Mapa amb les caselles del taulell ordenades
    private ArrayList<Player> players = new ArrayList<>();          ///< Llista de jugadors de la partida

    /**
     * @brief Constructor de Board per defecte
     * @pre true
     * @post Board creat
     */
    public Board () {}

    /**
     * @brief Setter del nombre de caselles
     * @pre true
     * @post s'ha guardat el numero de caselles
     */
    public void setBoxesNr(int number){
        boxes_nr = number;
    }

    /**
     * @brief Afegir un jugador nou a la partida
     * @pre true
     * @post El jugador nou ha estat afegit
     * @param player Jugador nou
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * @brief Moure el jugador de casella i gestionar el pas per la casella de inici
     * @pre true
     * @post El jugador s'ha mogut a la posició entrada i si ha passat per la casella de sortida, se l'hi ha pagat la recompensa
     * @param player Jugador actiu a moure
     * @param position posicio final del jugador
     * @param rewards posibles recompenses de la casella de sortida
     */
    public void movePlayer (Player player, int position, ArrayList<String> rewards) {
        boolean give_reward = player.getPosition() > position;
        if(give_reward){
            if(rewards.size() > 1) {
                int menu_option = 1;
                for (String reward : rewards) {
                    System.out.println(menu_option + " - " + reward);
                    menu_option++;
                }
                Scanner scan = new Scanner(System.in);
                int chosed_option = player.optionSelection("start",null,null,null,null,null,rewards.size()-1);
                String reward = rewards.get(chosed_option-1);
                if(reward.equals("terreny")){
                    Field field_reward = randomField();
                    if (field_reward == null) {
                        System.out.println("NO HI HA CAP TERRENY");
                    }
                    else {
                        player.addBox(field_reward);
                        field_reward.buy(player);
                        System.out.println("Has rebut " + field_reward.getName());
                    }
                }
                else{
                    player.charge(Integer.parseInt(reward));
                    System.out.println("Has rebut "+reward+"€");
                }
            }
            else{
                String reward = rewards.get(0);
                if(reward.equals("terreny")) {
                    Field field_reward = randomField();
                    player.addBox(field_reward);
                    field_reward.buy(player);
                    System.out.println("Has rebut " + field_reward.getName());
                }
                else{
                    player.charge(Integer.parseInt(reward));
                    System.out.println("Has rebut "+reward+"€");
                }
            }
        }
        player.movePlayer(position);
    }

    /**
     * @brief Afageix una casella al taulell
     * @pre true
     * @post La casella ha estat afegida
     * @param box Casella nova
     */
    public void addBox(Box box) {
        board.put(box.getPosition(),box);
    }

    /**
     * @brief Comprova si la casella te propietari
     * @pre true
     * @post s'ha retornat si la casella te propietari
     * @param box Casella de tipus terreny
     * @return true si la casella te propietari
     */
    public boolean haveOwner(Field box) {
        Field aux = (Field) board.get(box.getPosition());
        return aux.isBought();
    }

    /**
     * @brief Getter de la casella on estigui el jugador
     * @pre true
     * @post La casella on està el jugador ha estat retornada
     * @param player Jugador actiu
     * @return La casella de la posicio on està el jugador actiu
     */
    public Box getBox(Player player) {
        return board.get(player.getPosition());
    }

    /**
     * @brief Genera una casella de terreny de forma aleatoria
     * @pre true
     * @post La casella de terreny s'ha retornat
     * @return una cassella de terreny aleatoria
     */
    private Field randomField(){
        Random rand = new Random();
        int aux_nr = rand.nextInt(boxes_nr-1)+1;
        Field aux_field = null;
        if(haveAvailableFields()) {
            Box aux_box = board.get(aux_nr);
            boolean have_owner = true;
            if (aux_box.getType().equals("FIELD")) {
                aux_field = (Field) aux_box;
                if (!aux_field.isBought()) {
                    have_owner = false;
                }
            }
            while (!aux_box.getType().equals("FIELD") || have_owner) {
                aux_box = board.get(rand.nextInt(boxes_nr-1)+1);
                if (aux_box.getType().equals("FIELD")) {
                    aux_field = (Field) aux_box;
                    if (!aux_field.isBought()) {
                        have_owner = false;
                    }
                }
            }
        }
        return aux_field;
    }

    /**
     * @brief Comprova si queden terrenys sense propietari
     * @pre true
     * @post s'ha retornat si queden terrenys sense propietari
     * @return true si queden terrenys sense propietari, false altrament
     */
    private boolean haveAvailableFields(){
        Iterator<Integer> it = board.keySet().iterator();
        boolean found = false;
        while(it.hasNext() && !found){
            int box_nr = it.next();
            if (board.get(box_nr).getType() == "FIELD") {
                Field aux_field = (Field) board.get(box_nr);
                if (!haveOwner(aux_field)) {
                    found = true;
                }
            }
        }
        return found;
    }

    /**
     * @brief Gestiona les accions per quan el jugador està sense diners ( en fallida )
     * @pre true
     * @post el jugador ha realitzat les accions disponibles i s'ha comprovat si despres pot seguir jugant
     * @return true si el jugador pot seguir jugant, false altrament
     * @param current_player jugador actual
     * @param pay_amount quantitat a pagar
     * @param aux Classe movement per poder cridar a accions opcionals
     */
    public boolean isBankrupt(Player current_player, int pay_amount, Movement aux){
        boolean is_it = false;
        if(current_player.getLuckCards().isEmpty() && current_player.getFields().isEmpty()){ is_it = false; }
        else {
            boolean sell_action_done = false;
            boolean card_action_done = false;
            while (!sell_action_done && !card_action_done) {
                System.out.println("Hauria de triar una de les seguents opcions per afrontar el pagament:");
                int option_nr;
                System.out.println("0- Declarar-se en fallida");
                System.out.println("1- Vendre terrenys en la seva propietat");
                System.out.println("2- Utilitzar una targeta sort en propietat");
                int missing_money = pay_amount - current_player.getMoney();
                System.out.println("Necessites obtenir " + missing_money + ", quina opcio tries?");
                Scanner scan = new Scanner(System.in);
                option_nr = current_player.optionSelection("bankruptcy",null,null,null,null,null,0);
                while (option_nr < 0 || option_nr > 2) {
                    System.out.println("Opcio incorrecte, torna a provar");
                    option_nr = scan.nextInt();
                }
                if (option_nr == 1) {
                    while (!sell_action_done) {
                        if (current_player.getFields().isEmpty()) {
                            System.out.println("Cap terreny en propietat");
                        } else {
                            Sell sell = new Sell();
                            sell.execute(players, current_player,aux);
                            sell_action_done = true;
                        }
                    }
                }
                else if (option_nr == 2) {
                    if (current_player.getLuckCards().isEmpty()) {
                        System.out.println("Cap targeta sort en propietat");
                    }
                    else {
                        int charge_cards_nr = 0;
                        int card_nr = 1;
                        for (Card card : current_player.getLuckCards()) {
                            if (card.getType().equals("CHARGE")) {
                                CardCharge c = (CardCharge) card;
                                System.out.println("\t" + card_nr + "- Obtindras " + c.getQuantity() + "€");
                                charge_cards_nr++;
                            }
                            card_nr++;
                        }
                        System.out.println("Quina targeta vols utilitzar?");
                        System.out.println("Introdueix 0 per cancel·lar");
                        card_nr = scan.nextInt();
                        while (card_nr < 0 || card_nr > charge_cards_nr) {
                            System.out.println("Introduieixi una opcio valida");
                            card_nr = scan.nextInt();
                        }
                        if (card_nr == 0) {
                            card_action_done = true;
                        } else if (card_nr > 0) {
                            CardCharge chosed_card = (CardCharge) current_player.getLuckCards().get(card_nr - 1);
                            if (chosed_card.getQuantity() < missing_money) {
                                System.out.println("La targeta sel·leccionada no li reporta el suficient benefici de " + missing_money + "€");
                                card_action_done = true;
                            } else {
                                chosed_card.execute(this, current_player);
                                current_player.getLuckCards().remove(chosed_card);
                                card_action_done = true;
                            }
                        }
                    }
                }
                else {
                    System.out.println("El jugador " + current_player.getName() + "s'ha declarat en fallida");
                    current_player.goToBankruptcy();
                    return false;
                }
            }
            if(pay_amount > current_player.getMoney()){ is_it = true; }
        }
        return is_it;
    }

    /**
     * @brief Retorna el contador de propietats amb el grup igual a \p group_name.
     * @pre \p true
     * @post El numero de propietats amb \p group_name ha estat retornat.
     * @return numero de propietats amb el mateix \p group_name.
     */
    public int numberOfAgrupationField(String group_name){
        int number = 0;
        for(Iterator<Map.Entry<Integer,Box>> entries = board.entrySet().iterator(); entries.hasNext();){
            Map.Entry<Integer, Box> entry = entries.next();
            if(entry.getValue().getType().equals("FIELD")){
                Field aux = (Field) entry.getValue();
                if(aux.getGroup().equals(group_name)) number++;
            }
        }
        return number;
    }

    /**
     * @brief Transfereix totes les propietats, diners i targetes sort a la banca o a un altre jugador.
     * @pre Si player_get == null serà la banca, un jugador contrari altrament
     * @post Totes les propietats, diners i targetes, s'han transferit a Jugador o a la banca
     */
    public void transferProperties(Player player_give, Player player_get, Movement movement){
        if(player_get == null){ //banca
            for (Field field : player_give.getFields()){
                field.sell();
            }
            player_give.getFields().clear();
            player_give.pay(player_give.getMoney());
            for(Card card : player_give.getLuckCards()){
                movement.getCards().add(0,card);
            }
            player_give.getLuckCards().clear();
        }
        else{
            for (Field field : player_give.getFields()){
                player_get.addBox(field);
            }
            player_give.getFields().clear();
            player_get.charge(player_give.getMoney());
            player_give.pay(player_give.getMoney());
            for(Card card : player_give.getLuckCards()){
                player_get.getLuckCards().add(card);
            }
            player_give.getLuckCards().clear();
        }
    }

    /**
     * @brief Calcula la mida del taulell
     * @pre true
     * @post s'ha retornat la mida del taulell
     * @return la mida del taulell
     */
    public int getSize() {
        return board.size();
    }

    /**
     * @brief toString per mostrar l'informació del Taulell per text.
     * @pre \p true
     * @post el tualell ha estat mostrat per pantalla amb tota la seva informació per poder seguir la partida adequadament.
     * @return salt de linea
     */
    @Override
    public String toString() {
        System.out.println("---------- TAULELL ----------");
        for (int i = 1;i<=board.size();i++) {
            board.get(i).print(players);
        }

        System.out.println("\n---------- JUGADORS ----------");
        for(Player player : players) {
            System.out.println(player.toString());
        }
        return "";
    }



}

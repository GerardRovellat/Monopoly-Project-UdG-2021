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
     * @brief Constructor de Board per defecte.
     * @pre \p true
     * @post Board creat.
     */
    public Board () {}

    /**
     * @brief Setter del nombre de caselles.
     * @pre \p true
     * @post s'ha guardat el numero de caselles.
     * @param number numero de la casella.
     */
    public void setBoxesNr(int number){
        boxes_nr = number;
    }

    /**
     * @brief Afegir un jugador nou a la partida.
     * @pre \p true
     * @post El jugador nou ha estat afegit.
     * @param player Jugador nou.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * @brief Moure el jugador de casella i gestionar el pas per la casella de inici
     * @pre true
     * @post El jugador s'ha mogut a la posició entrada i si ha passat per la casella de sortida, se l'hi ha pagat la recompensa
     * @param player        Jugador actiu a moure
     * @param position      posicio final del jugador
     * @param rewards       posibles recompenses de la casella de sortida
     */
    public void movePlayer (Player player, int position, ArrayList<String> rewards,OutputManager output) {
        boolean give_reward = player.getPosition() >= position;
        if(give_reward){
            if(rewards.size() > 1) {
                int menu_option = 1;
                for (String reward : rewards) {
                    System.out.println(menu_option + " - " + reward);
                    menu_option++;
                }
                int chosed_option = player.optionSelection("start",null,null,null,null,null,rewards.size()-1,null);
                while(chosed_option<1 || chosed_option > rewards.size()){
                    System.out.println("ERROR: Rang incorrecte, torna-hi...");
                    chosed_option = player.optionSelection("start",null,null,null,null,null,rewards.size()-1,null);
                }
                String reward = rewards.get(chosed_option-1);
                if(reward.equals("terreny")){
                    BoxField field_reward = randomField();
                    if (field_reward == null) {
                        System.out.println("NO HI HA CAP TERRENY");
                        output.fileWrite(player.getName() + "> No hi ha cap terreny");
                    }
                    else {
                        player.addBox(field_reward);
                        field_reward.buy(player);
                        System.out.println("Has rebut " + field_reward.getName());
                        output.fileWrite(player.getName() + "> Ha rebut: " + field_reward.getName());
                    }
                }
                else{
                    player.charge(Integer.parseInt(reward));
                    System.out.println("Has rebut "+reward+"€");
                    output.fileWrite(player.getName() + "> Ha rebut: "+reward+"€");

                }
            }
            else{
                String reward = rewards.get(0);
                if(reward.equals("terreny")) {
                    BoxField field_reward = randomField();
                    if (field_reward == null) {
                        System.out.println("NO HI HA CAP TERRENY");
                        output.fileWrite(player.getName() + "> No hi ha cap terreny");
                    }
                    else {
                        player.addBox(field_reward);
                        field_reward.buy(player);
                        System.out.println("Has rebut " + field_reward.getName());
                        output.fileWrite(player.getName() + "> Ha rebut: " + field_reward.getName());
                    }
                }
                else{
                    player.charge(Integer.parseInt(reward));
                    System.out.println("Has rebut "+reward+"€");
                    output.fileWrite(player.getName() + "> Ha rebut: "+reward+"€");
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
    public boolean haveOwner(BoxField box) {
        BoxField aux = (BoxField) board.get(box.getPosition());
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
    private BoxField randomField(){
        Random rand = new Random();
        BoxField aux_field = null;
        if(haveAvailableFields()) {
            int aux_nr = rand.nextInt(boxes_nr-1)+1;
            Box aux_box = board.get(aux_nr);
            boolean have_owner = true;
            if (aux_box.getType().equals("FIELD")) {
                aux_field = (BoxField) aux_box;
                if (!aux_field.isBought()) {
                    have_owner = false;
                }
            }
            while (!aux_box.getType().equals("FIELD") || have_owner) {
                aux_box = board.get(rand.nextInt(boxes_nr-1)+1);
                if (aux_box.getType().equals("FIELD")) {
                    aux_field = (BoxField) aux_box;
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
            if (board.get(box_nr).getType().equals("FIELD")) {
                BoxField aux_field = (BoxField) board.get(box_nr);
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
     * @return true si el jugador no pot seguir jugant, false altrament
     * @param current_player    jugador actual
     * @param pay_amount        quantitat a pagar
     * @param aux               Classe movement per poder cridar a accions opcionals
     */
    public boolean isBankrupt(Player current_player, int pay_amount, Movement aux){
        OutputManager output = aux.getOutput();
        if(!current_player.getLuckCards().isEmpty() || !current_player.getFields().isEmpty()) {
            boolean sell_action_done = false;
            boolean card_action_done = false;
            while (!sell_action_done && !card_action_done) {
                Scanner scan = new Scanner(System.in);
                int missing_money = pay_amount - current_player.getMoney();
                int option_nr = selectOption(current_player,pay_amount, missing_money,scan);
                if (option_nr == 1) {
                    while (!sell_action_done) {
                        sell_action_done = tryToSellToAlive(current_player,aux);
                    }
                }
                else if (option_nr == 2) {
                    card_action_done = tryToRunCardToAlive(current_player,missing_money,scan);
                }
                else {
                    System.out.println("El jugador " + current_player.getName() + "s'ha declarat en fallida");
                    output.fileWrite(current_player.getName() + "> Es declara en fallida");
                    current_player.goToBankruptcy();
                    return true;
                }
            }
            if(pay_amount > current_player.getMoney()) {
                output.fileWrite(current_player.getName() + "> Es declara en fallida");
                return true;
            }
            else {
                output.fileWrite(current_player.getName() + "> Aconseguix els diners");
                return false;
            }
        }
        output.fileWrite(current_player.getName() + "> Es declara en fallida");
        return true;
    }

    /**
     * @brief Intentara executar una targeta de la ma per tal de no declarar-se en fallida.
     * @pre \p true
     * @post Retorna \p true si s'ha executat una targeta, \p false altrament.
     * @param current_player        Jugador actual.
     * @param missing_money         Diners que falten per afrontar el pagament.
     * @param scan                  Scanner d'entrada per l'usuari.
     * @return \p true si s'ha pogut executar la carta, \p false altrament.
     */
    private boolean tryToRunCardToAlive(Player current_player, int missing_money, Scanner scan){
        boolean card_action_done = false;
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
            if (card_nr != 0) {
                CardCharge chosed_card = (CardCharge) current_player.getLuckCards().get(card_nr - 1);
                if (chosed_card.getQuantity() < missing_money) {
                    System.out.println("La targeta sel·leccionada no li reporta el suficient benefici de " + missing_money + "€");
                } else {
                    chosed_card.execute(current_player);
                    current_player.getLuckCards().remove(chosed_card);
                }
            }
            card_action_done = true;
        }

        return  card_action_done;
    }

    /**
     * @brief Realitzarà una venta per tal d'afrontar el pagament i no quedar-se en fallida.
     * @pre \p true
     * @post Retorna true si s'ha pogut vendre.
     * @param current_player        Jugador actual.
     * @param aux                   Moviment el qual pertanyen els jugadors.
     * @return \p true si s'ha pogut realitzar la venta, \p false altrament.
     */
    private boolean tryToSellToAlive(Player current_player, Movement aux){
        boolean sell_action_done = false;
        if (current_player.getFields().isEmpty()) {
            System.out.println("Cap terreny en propietat");
        } else {
            OpActSell sell = new OpActSell();
            sell.execute(players, current_player,aux);
            sell_action_done = true;
        }
        return sell_action_done;
    }

    /**
     * @brief Sel·lecciona una opcio al pel metode de \p isBankrupt.
     * @pre \p true
     * @post Retorna la opció sel·leccionada.
     * @param current_player        Jugador actual.
     * @param pay_amount            Quantitat que s'ha de pagar.
     * @param missing_money         Quantitat que es necessita per afrontar el pagament.
     * @return numero de opcio triada.
     */
    private int selectOption(Player current_player, int pay_amount, int missing_money,Scanner scan){
        System.out.println("Hauria de triar una de les seguents opcions per afrontar el pagament de "+pay_amount+":");
        int option_nr;
        System.out.println("0- Declarar-se en fallida");
        System.out.println("1- Vendre terrenys en la seva propietat");
        System.out.println("2- Utilitzar una targeta sort en propietat");
        System.out.println("Necessites obtenir " + missing_money + ", quina opcio tries?");
        option_nr = current_player.optionSelection("bankruptcy",null,null,null,null,null,0,null);
        while (option_nr < 0 || option_nr > 2) {
            System.out.println("Opcio incorrecte, torna a provar");
            option_nr = scan.nextInt();
        }
        return option_nr;
    }

    /**
     * @brief Retorna el contador de propietats amb el grup igual a \p group_name.
     * @pre \p true
     * @post El numero de propietats amb \p group_name ha estat retornat.
     * @param group_name        Grup el qual pertany el \p Field.
     * @return numero de propietats amb el mateix \p group_name.
     */
    public int numberOfAgrupationField(String group_name){
        int number = 0;
        for (Map.Entry<Integer, Box> entry : board.entrySet()) {
            if (entry.getValue().getType().equals("FIELD")) {
                BoxField aux = (BoxField) entry.getValue();
                if (aux.getGroup().equals(group_name)) number++;
            }
        }
        return number;
    }

    /**
     * @brief Transfereix totes les propietats, diners i targetes sort a la banca o a un altre jugador.
     * @pre Si player_get == null serà la banca, un jugador contrari altrament
     * @post Totes les propietats, diners i targetes, s'han transferit a Jugador o a la banca
     * @param player_give       Jugador que ha de donar les propietats.
     * @param player_get        Jugador que ha de rebre les propietatd.
     * @param movement          Moviment el qual estava fent.
     */
    public void transferProperties(Player player_give, Player player_get, Movement movement){
        if(player_get == null){ //banca
            for (BoxField field : player_give.getFields()){
                field.sell();
            }
            player_give.getFields().clear();
            player_give.pay(player_give.getMoney());
            for(Card card : player_give.getLuckCards()){
                movement.getCards().add(0,card);
            }
        }
        else{
            for (BoxField field : player_give.getFields()){
                player_get.addBox(field);
            }
            player_give.getFields().clear();
            player_get.charge(player_give.getMoney());
            player_give.pay(player_give.getMoney());
            for(Card card : player_give.getLuckCards()){
                player_get.getLuckCards().add(card);
            }
        }
        player_give.getLuckCards().clear();
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
        StringBuilder output_text = new StringBuilder();
        output_text.append("---------- TAULELL ----------\n");
        for (int i = 1;i<=board.size();i++) {
            output_text.append(board.get(i).print(players));
        }

        output_text.append("\n---------- JUGADORS ----------\n");
        for(Player player : players) {
            output_text.append(player.toString());
        }
        return output_text.toString();
    }

}

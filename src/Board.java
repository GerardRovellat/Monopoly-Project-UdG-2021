import java.util.*;

public class Board {
    private int boxes_nr;
    private SortedMap<Integer,Box> board = new TreeMap<>();
    private LinkedHashMap<String,Player> players = new LinkedHashMap<>();

    public Board () {}

    public void setBoxesNr(int number){
        boxes_nr = number;
    }

    public void addPlayer(Player player) {
        players.put(player.getName(),player);
    }

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
                int chosed_option = scan.nextInt();
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

    public void addBox(Box box) {
        board.put(box.getPosition(),box);
    }

    public boolean haveOwner(Field box) {
        Field aux = (Field) board.get(box.getPosition());
        return aux.isBought();
    }

    public Box getBox(Player player) {
        return board.get(player.getPosition());
    }

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

    public boolean isBankrupt(Player actual_player, int pay_amount){
        boolean is_it = false;
        if(actual_player.getLuckCards().isEmpty() && actual_player.getFields().isEmpty()){ is_it =true; }
        else {
            System.out.println("Hauria de triar una de les seguents opcions per afrontar el pagament:");
            int option_nr;
            System.out.println("0- Declarar-se en fallida");
            System.out.println("1- Vendre terrenys en la seva propietat");
            if(actual_player.getFields().isEmpty()){
                System.out.println("Cap terreny en propietat");
            }
            else{
                int field_nr = 1;
                for (Field field : actual_player.getFields()) {
                    System.out.println("\t"+field_nr+"- "+field.getName()+" (Valor del terreny "+field.getPrice()+"€)");
                }
            }
            System.out.println("2- Utilitzar una targeta sort en propietat");
            int charge_cards_nr = 0;
             if (actual_player.getLuckCards().isEmpty()){
                 System.out.println("Cap targeta sort en propietat");
             }
             else{
                 int card_nr = 1;
                 for (Card card : actual_player.getLuckCards()) {
                     if (card.getType().equals("CHARGE")) {
                         CardCharge c = (CardCharge) card;
                         System.out.println("\t"+card_nr+"- Obtindras "+ c.getQuantity() +"€");
                         charge_cards_nr++;
                     }
                     card_nr++;
                 }
             }
            int missing_money = pay_amount - actual_player.getMoney();
            System.out.println("Necessites obtenir "+missing_money+", quina opcio tries?");
            Scanner scan = new Scanner(System.in);
            option_nr = scan.nextInt();
            while(option_nr < 0 || option_nr > 2){
                System.out.println("Opcio incorrecte, torna a provar");
                option_nr = scan.nextInt();
            }
            if(option_nr == 1){
                int field_nr;
                int confirm = 2;
                Field field_to_sell = null;
                while(confirm == 2){
                    System.out.println("Quin terreny vols vendre?");
                    field_nr = scan.nextInt();
                    field_to_sell = actual_player.getFields().get(field_nr-1);
                    System.out.println(field_to_sell.toString());
                    System.out.println("Segur que vols vendre aquesta propietat? (1-Si 2-No)");
                    while(confirm != 1 || confirm != 2){
                        System.out.println("Tria una opcio correcte");
                        confirm = scan.nextInt();
                    }
                }
                int sell_price;
                System.out.println("Per quin preu vols començar la venta?");
                sell_price = scan.nextInt();
                System.out.println("La subhasta per "+field_to_sell.getName()+" comença per "+sell_price+"€");
                LinkedHashMap<String,Player> auction_players = new LinkedHashMap<>();
                auction_players = players;
                auction_players.remove(actual_player.getName());
                int offer, max_offer=-1;
                String winner = null;
                while(auction_players.size() > 1){
                    for(Map.Entry<String,Player> entry : players.entrySet()){
                        String name_of_player = entry.getKey();
                        int actual_money = players.get(name_of_player).getMoney();
                        System.out.println(name_of_player+" tens "+actual_money+"€");
                        System.out.println("Que ofereixes? (-1 per retirar-se):");
                        offer = scan.nextInt();
                        while (actual_money < offer && offer != -1){
                            System.out.println("Erroni, no pots pagar més de "+actual_money+", prova de nou");
                            offer = scan.nextInt();
                        }
                        if(offer == -1){
                            auction_players.remove(name_of_player);
                            System.out.println(name_of_player+" s'ha retirat de la subhasta");
                        }
                        else{
                            if(max_offer < offer){
                                max_offer = offer;
                                winner = name_of_player;
                            }
                        }
                    }
                }
                if(winner == null){
                    System.out.println("Cap jugador ha comprat la propietat "+field_to_sell.getName());
                    is_it = true;
                }
                else{
                    players.get(winner).pay(max_offer);
                    players.get(actual_player).charge(max_offer);
                    System.out.println("El jugador "+winner+" ha comprat "+field_to_sell+" per "+max_offer);
                }
            }
            else if(option_nr == 2){
                int card_nr;
                System.out.println("Quina targeta vols utilitzar? (introdueix 0 per declarar-se en fallida)");
                card_nr = scan.nextInt();
                CardCharge chosed_card =(CardCharge) actual_player.getLuckCards().get(card_nr-1);
                while(card_nr < 0 || card_nr > charge_cards_nr || chosed_card.getQuantity() < missing_money){
                    System.out.println("Introdueix una carta valida");
                    card_nr = scan.nextInt();
                    chosed_card = (CardCharge) actual_player.getLuckCards().get(card_nr-1);
                }
                if(card_nr == 0){
                    System.out.println("El jugador "+actual_player.getName()+"s'ha declarat en fallida");
                    is_it = true;
                }
                else{
                    actual_player.charge(chosed_card.getQuantity());
                    System.out.println("En "+actual_player.getName()+" ha rebut "+chosed_card.getQuantity());
                    actual_player.getLuckCards().remove(chosed_card);
                }
            }
        }
        return is_it;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$$$$$
     */
    public int getSize() {
        return board.size();
    }

    @Override
    public String toString() {
        System.out.println("---------- TAULELL ----------");
        for (int i = 1;i<=board.size();i++) {
            board.get(i).print();
        }

        System.out.println("\n---------- JUGADORS ----------");
        for(Map.Entry<String,Player> entry : players.entrySet()) {
            System.out.println(entry.getValue().toString());
        }

        return "\n";
    }



}

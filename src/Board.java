import java.util.*;

public class Board {
    private int boxes_nr;
    private SortedMap<Integer,Box> board = new TreeMap<>();
    private ArrayList<Player> players = new ArrayList<>();

    public Board () {}

    public void setBoxesNr(int number){
        boxes_nr = number;
    }

    public void addPlayer(Player player) {
        players.add(player);
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

    public boolean isBankrupt(Player current_player, int pay_amount, Movement aux){
        boolean is_it = false;
        if(current_player.getLuckCards().isEmpty() && current_player.getFields().isEmpty()){ is_it =true; }
        else {
            boolean sell_action_done = false;
            boolean card_action_done = false;
            while (!sell_action_done || !card_action_done) {
                System.out.println("Hauria de triar una de les seguents opcions per afrontar el pagament:");
                int option_nr;
                System.out.println("0- Declarar-se en fallida");
                System.out.println("1- Vendre terrenys en la seva propietat");
                System.out.println("2- Utilitzar una targeta sort en propietat");
                int missing_money = pay_amount - current_player.getMoney();
                System.out.println("Necessites obtenir " + missing_money + ", quina opcio tries?");
                Scanner scan = new Scanner(System.in);
                option_nr = scan.nextInt();
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
                            sell_action_done = sell.execute(players, current_player,aux);
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
                }
            }
            if(pay_amount > current_player.getMoney()){ is_it = true; }
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
            board.get(i).print(players);
        }

        System.out.println("\n---------- JUGADORS ----------");
        for(Player player : players) {
            System.out.println(player.toString());
        }

        return "\n";
    }



}

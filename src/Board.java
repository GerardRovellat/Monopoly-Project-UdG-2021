import java.util.*;

public class Board {
    private int boxes_nr;
    private SortedMap<Integer,Box> board = new TreeMap<>();
    private HashMap<String,Player> players = new HashMap<>();

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
                String reward = rewards.get(chosed_option);
                if(reward.equals("terreny")){
                    Field field_reward = randomField();
                    player.addBox(field_reward);
                    field_reward.buy(player);
                    System.out.println("Has rebut "+field_reward.getName());
                }
                else{
                    player.charge(Integer.parseInt(reward));
                    System.out.println("Has rebut "+reward+"€");
                }
            }
            else{
                String reward = rewards.get(1);
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
        Field aux_field = (Field) board.get(aux_nr);
        if(haveAvailableFields()){
            while(!aux_field.type.equals("FIELD") && haveOwner(aux_field)){
                aux_nr = rand.nextInt(boxes_nr-1)+1;
                aux_field = (Field) board.get(aux_nr);
            }
        }
        return aux_field;
    }

    private boolean haveAvailableFields(){
        Iterator<Integer> it = board.keySet().iterator();
        boolean found = false;
        while(it.hasNext() && !found){
            int box_nr = it.next();
            Field aux_field = (Field) board.get(box_nr);
            if(aux_field.type.equals("FIELD") && !haveOwner(aux_field)){
                found = true;
            }
        }
        return found;
    }

    public boolean isBankrupt(Player actual_player, int pay_amount){
        if(actual_player.getLuckCards().isEmpty() && actual_player.getFields().isEmpty()){ return false; }
        else {
            System.out.println("Hauria de triar una de les seguents opcions per afrontar el pagament:");
            int option_nr;
            for (option_nr = 1; option_nr <= 2; option_nr++) {
                System.out.println(option_nr + "- Vendre terrenys en la seva propietat");
                if(actual_player.getFields().isEmpty()){
                    System.out.println("Cap terreny en propietat");
                }
                else{
                    int field_nr = 1;
                    for (Field field : actual_player.getFields()) {
                        System.out.println("\t"+field_nr+"- "+field.getName()+" (Valor del terreny "+field.getPrice()+"€)");
                    }
                }
                System.out.println(option_nr + "- Utilitzar una targeta sort en propietat");
                 if (actual_player.getLuckCards().isEmpty()){
                     System.out.println("Cap targeta sort en propietat");
                 }
                 else{
                     int card_nr = 1;
                     for (Card card : actual_player.getLuckCards()) {
                         if (card.getType().equals("CHARGE")) {
                             CardCharge c = (CardCharge) card;
                             System.out.println("\t"+card_nr+"- Obtindras "+ c.getQuantity() +"€");
                         }
                         card_nr++;
                     }
                 }
            }
            int missing_money = pay_amount - actual_player.getMoney();
            System.out.println("Necessites "+missing_money+", quina opcio tries?");
            Scanner scan = new Scanner(System.in);
            option_nr = scan.nextInt();
            while(option_nr < 0 || option_nr > 2){
                System.out.println("Opcio incorrecte, torna a provar");
                option_nr = scan.nextInt();
            }
            if(option_nr == 1){
                int field_nr;
                int confirm = 2;
                Field field_to_sell;
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
                //FALTA FER
            }
            else{
                int card_nr;
                System.out.println("Quina targeta vols utilitzar?");
                card_nr = scan.nextInt();
                System.out.println();
            }

        }
        return false;
    }

}

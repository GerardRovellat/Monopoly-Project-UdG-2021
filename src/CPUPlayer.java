
import java.util.*;

public class CPUPlayer extends Player{

    HashMap<Integer,String> types = new HashMap<>();

    /**
     * @param name             nom del Jugador.
     * @param initial_money    quantitat de diners inicials dels que disposa un Jugador.
     * @param initial_position posició inicial del Jugador.
     * @brief Constructor de Player.
     * @pre true
     * @post Crea un jugador amb els atributs entrats.
     */
    public CPUPlayer(String name, int initial_money, int initial_position) {
        super(name, initial_money, initial_position,"CPU");
        startTypes();
    }

    public int optionSelection(String type,Player player, Field field,ArrayList<Integer> options,ArrayList<Player> players,Card card, int max) {
        int return_value;
        switch (type) {
            case "optionalActionSelector":
                //return_value = optionalActionSelector(player);
                return_value = 0;
                break;
            case "start":
                return_value = start(player);
                break;
            case "buy":
                return_value = buy(player,field);
                break;
            case "confirmation":
                return_value = 0;
                break;
            case "buildChoice":
                return_value = buildChoice(player,field);
                break;
            case "build":
                return_value = build(player,field);
                break;
            case "buildApartment":
                return_value = buildApartment(player,field,max);
                break;
            case "betQuantity":
                return_value = betQuantity(player);
                break;
            case "betValue":
                return_value = betValue(betQuantity(player));
                break;
            case "cardGetPlayerSelect":
                return_value = cardGetPlayerSelect(options,players);
                break;
            case "cardGetFieldSelect":
                return_value = cardGetFieldSelect(player);
                break;
            case "cardGivePlayerSelect":
                return_value = cardGivePlayerSelect(options,players);
                break;
            case "cardGiveFieldSelect":
                return_value = cardGiveFieldSelect(player);
                break;
            case "cardPayPlayerSelect":
                return_value = cardPayPlayerSelect(player,players);
                break;
            case "postposableLuckCardChoice":
                return_value = postposableLuckCardChoice(card);
                break;
            default:
                return_value = 0;
        }
        System.out.println("CPU CHOCIE: " + return_value);
        return return_value;
    }

    public int integerValueSelection(int min, int max, String type, Player player) {
        Random rand = new Random();
        int result = rand. nextInt(max-min) + min;
        return result;
    }

    public String stringValueSelection(int min, int max, String type, Player player) {
        switch (type) {
            case "throwDice" :
                return "\n";
            default:
                return "";
        }
    }

    private void startTypes() {
        types.put(0,"optionalActionSelector");
        types.put(1,"start");

        types.put(2,"buy");
        types.put(3,"buyConfirmation");


        types.put(11,"buildChoice");
        types.put(4,"build");
        types.put(4,"buildApartment");

        types.put(5,"betQuantity");
        types.put(6,"betValue");
        types.put(7,"postposableLuckCardChoice");

        // CardGet
        types.put(8,"cardGetPlayerSelect");
        types.put(9,"cardGetFieldSelect");

        types.put(10,"cardPayPlayerSelect");
    }

    private int random(ArrayList<Integer> options) {
        Random rand = new Random();
        int result = rand.nextInt(options.size());
        return options.get(result);
    }

    private int optionalActionSelector(Player player) {
        HashMap<Integer,Integer> bestMoves = new HashMap<>();
        bestMoves.put(0,50);

        if (player.getMoney()<=20000) bestMoves.put(1,20-(player.getMoney()/1000)*5);  // Ex: money = 5000 => 5000/1000 = 5 => 20-5 = 15 => 15*5 = 75%
        else bestMoves.put(1,0);

        if (player.getMoney()>=20000) bestMoves.put(2,player.getMoney()/1000);
        else bestMoves.put(2,0);

        if (player.getMoney()<=5000) bestMoves.put(3,player.getMoney()/1000*20);  // Ex: money = 3000 => 3000/1000 = 3 => 5-3 = 2 => 2*20 = 40%
        else bestMoves.put(3,0);

        List<Card> cards = player.getLuckCards();
        int prob = 0;
        for (Card aux : cards) {
            if (aux.getType()=="CHARGE") prob = prob + 40;
            else if (aux.getType()=="GET") prob = prob + 50;
            else if (aux.getType()=="GO") prob = prob + 10;
        }
        bestMoves.put(4,prob);
        int bestMove = 0;
        for (int i=0;i<4;i++) {
            if (bestMoves.get(i).byteValue() > bestMoves.get(bestMove).longValue()) bestMove = i;
        }
        return bestMove;
    }

    private int start(Player player) {
        if (player.getMoney() < 15000) return 1;
        else return 2;
    }

    private int buy(Player player,Field field) {
        if (player.getMoney()-field.getPrice() >= 2000) return 1;
        else return 0;
    }

    // PRE: Construible
    private int buildChoice(Player player, Field field) {
        if (player.getMoney() < 10000) return 0;
        else return 1;
    }

    private int build(Player player, Field field) {
        if (field.hotelBuildable()) {
            if (player.getMoney()-field.priceToBuild() > 2000) return 2;
        }
        else if (field.houseBuildable()) {
            if (player.getMoney()-field.priceToBuild() > 2000) return 1;
        }
        return 0;
    }

    private int buildApartment(Player player, Field field, int max) {
        int max_to_buid = (player.getMoney() / 100) * 30 / field.priceToBuild();
        if (max_to_buid >= max) return max;
        else return max_to_buid;
    }


    private int betQuantity(Player player) {
        return (player.getMoney()/10) ; //10% de aposta
    }

    private int betValue(int betQuantity) {
        Random rand = new Random();
        if (betQuantity < 5000) return rand.nextInt(11-8) +8; // 8->11
        else if (betQuantity < 10000) return rand.nextInt(9-5) +5; // 5->9
        return rand.nextInt(7-3) +3; // 3->7
    }

    private int cardGetPlayerSelect (ArrayList<Integer> options,ArrayList<Player> players) {
        int min_fields = -1;
        int player_chosen = -1;
        for (int i=0;i<players.size();i++) {
            if (!options.contains(i) && players.get(i).getFields().size() > min_fields) {
                min_fields = players.get(i).getFields().size();
                player_chosen = i;
            }
        }
        if (player_chosen == -1) {
            System.out.println("HI HA HAGUT UN ERROR");  // Introduir exception
        }
        return player_chosen;
    }

    private int cardGetFieldSelect (Player fields_owner) {
        ArrayList<Field> fields = fields_owner.getFields();
        int iterator = 0;
        int result = 0;
        int value = fields.get(0).getPrice();
        for (Field aux : fields) {
            if (aux.getPrice() > value) result = iterator;
            iterator++;
        }
        return result;
    }

    private int cardGivePlayerSelect (ArrayList<Integer> options,ArrayList<Player> players) {
        int min_fields = -1;
        int player_chosen = -1;
        for (int i=0;i<players.size();i++) {
            if (!options.contains(i) && players.get(i).getFields().size() > min_fields) {
                min_fields = players.get(i).getFields().size();
                player_chosen = i;
            }
        }
        if (player_chosen == -1) {
            System.out.println("HI HA HAGUT UN ERROR");  // Introduir exception
        }
        return player_chosen;
    }

    private int cardGiveFieldSelect (Player fields_owner) {
        ArrayList<Field> fields = fields_owner.getFields();
        int iterator = 0;
        int result = 0;
        int value = fields.get(0).getPrice();
        for (Field aux : fields) {
            if (aux.getPrice() < value) {
                result = iterator;
                value = aux.getPrice();
            }
            iterator++;
        }
        return result;
    }

    // PRE: minim 2 jugadors
    private int cardPayPlayerSelect (Player current_player,ArrayList<Player> players) {
        int result = 0;
        int min_value = -1;
        if (players.get(0) != current_player) min_value = players.get(0).getMoney();
        else {
            min_value = players.get(1).getMoney();
            result = 1;
        }
        for (Player aux : players) {
            if (aux != current_player && aux.getMoney() < min_value) {
                result = players.indexOf(aux);
                min_value = aux.getMoney();
            }
        }
        if (result == -1 || min_value == -1) ;//Throw error
        return result;
    }

    private int postposableLuckCardChoice(Card card) {
        if (card.getType() == "CHARGE" || card.getType() == "GET" || card.getType() == "GO") return 1;
        else return 0;
    }

}

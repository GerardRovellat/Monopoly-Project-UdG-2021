
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

    public int optionSelection(String type,Player player, Field field) {
        switch (type) {
            case "optionalActionSelector":
                return optionalActionSelector(player);
            case "start":
                return start(player);
            case "buy":
                return buy(player,field);
            case "buyConfirmation":
                return 0;
            case "build":
                return build(player,field);
            case "betQuantity":
                return betQuantity(player);
            case "betValue":
                return betValue(betQuantity(player));
            default:
                return 0;
        }
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
        types.put(1,"optionalActionSelector");
        types.put(2,"start");

        types.put(3,"buy");
        types.put(4,"buyConfirmation");
        types.put(5,"build");

        types.put(6,"betQuantity");
        types.put(7,"betValue");

        // COMANDES DIRECTES
        types.put(3,"Build");
        types.put(3,"Build");
        types.put(3,"Build");
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
        if (player.getMoney() < 15000) return 0;
        else return 1;
    }

    private int buy(Player player,Field field) {
        if (player.getMoney()-field.getPrice() >= 2000) return 1;
        else return 0;
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

    private int betQuantity(Player player) {
        return (player.getMoney()/10) ; //10% de aposta
    }

    private int betValue(int betQuantity) {
        Random rand = new Random();
        if (betQuantity < 5000) return rand.nextInt(11-8) +8; // 8->11
        else if (betQuantity < 10000) return rand.nextInt(9-5) +5; // 5->9
        return rand.nextInt(7-3) +3; // 3->7
    }

}

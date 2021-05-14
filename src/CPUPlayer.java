
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
        super(name, initial_money, initial_position);
    }

    public int optionSelection(ArrayList<Integer> options, Scanner scan, String type) {
        Random rand = new Random();
        int result = rand.nextInt(options.size());
        return options.get(result);
    }

    public int valueSelection(int min, int max, Scanner scan, String type) {
        Random rand = new Random();
        int result = rand. nextInt(max-min) + min;
        return result;
    }

    private void startTypes() {
        types.put(0,"ThrowDice");
        types.put(1,"OptionalActionSelector");
        types.put(3,"Start");

        types.put(2,"Buy");
        types.put(2,"BuyConfirmation");
        types.put(3,"Build");

        types.put(3,"BetQuantity");
        types.put(3,"BetValue");

        // COMANDES DIRECTES
        types.put(3,"Build");
        types.put(3,"Build");
        types.put(3,"Build");
    }
}

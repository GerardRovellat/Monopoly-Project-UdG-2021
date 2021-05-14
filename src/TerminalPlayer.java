import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class TerminalPlayer extends Player{
    Scanner scan = new Scanner(System.in);

    /**
     * @param name             nom del Jugador.
     * @param initial_money    quantitat de diners inicials dels que disposa un Jugador.
     * @param initial_position posició inicial del Jugador.
     * @brief Constructor de jugador amb terminal
     * @pre true
     * @post Crea un jugador amb els atributs entrats.
     */
    public TerminalPlayer(String name, int initial_money, int initial_position) {
        super(name, initial_money, initial_position,"USER");
    }

    public int optionSelection(String type, Player player, Field field) {
        return scan.nextInt();
    }

    public int integerValueSelection(int min, int max, String type, Player player) {
        return scan.nextInt();
    }

    public String stringValueSelection(int min, int max, String type, Player player) {
        return scan.next();
    }
}

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class TerminalPlayer extends Player{

    /**
     * @param name             nom del Jugador.
     * @param initial_money    quantitat de diners inicials dels que disposa un Jugador.
     * @param initial_position posició inicial del Jugador.
     * @brief Constructor de jugador amb terminal
     * @pre true
     * @post Crea un jugador amb els atributs entrats.
     */
    public TerminalPlayer(String name, int initial_money, int initial_position) {
        super(name, initial_money, initial_position);
    }

    public int optionSelection(ArrayList<Integer> options, Scanner scan,String type) {
        return scan.nextInt();
    }

    public int valueSelection(int min, int max, Scanner scan,String type) {
        return scan.nextInt();
    }
}

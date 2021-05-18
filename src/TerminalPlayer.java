import java.util.ArrayList;
import java.util.InputMismatchException;
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

    public int optionSelection(String type, Player player, Field field,ArrayList<Integer> options,ArrayList<Player> players,Card card, int value) {
        int number = 0;
        boolean valid = false;
        do {
            try {
                number = scan.nextInt();
                valid = true;
            } catch (InputMismatchException e_format) {
                scan.nextLine();
                System.out.println("FORMAT ENTRAT INCORRECTE: Torna-hi...");
            }
        }while (!valid);
        return number;
    }

    public String stringValueSelection(String type, Player player, Field field, int value, int second_value) {
        return scan.next();
    }
}

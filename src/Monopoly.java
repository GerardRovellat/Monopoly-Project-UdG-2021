import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.util.Pair;

/**
 * @author Marc Got
 * @file Monopoly.java
 * @class Monopoly
 * @brief Classe que s'encarrega d'administrar l'inicialització, els torns i funcionament general
 * i la finalització del joc Monopoly.
 */
public class Monopoly {
    private ArrayList<Player> players = new ArrayList<>();      ///< Llista de Jugadors del Monopoly.
    private Board board;                                        ///< Tauler del Monopoly.
    private int initial_money;                                  ///< Diners inicials quan es comença una partida.
    private ArrayList<String> start_rewards;                    ///< Recompenses de casella sortida "Start".
    private Pair<Integer,Integer> dice_result;                  ///< Resultat dels daus tirats.
    private int current_player_iterator = 0;                    ///< Iterador que recorre Llista players.
    private Player current_player;                              ///< Jugador actual.
    private ArrayList<optionalActions> optional_actions;        ///< Llista d'accions opcionals.
    private ArrayList<Card> cards;
    Scanner scan = new Scanner(System.in);                           ///< Llista de targetes sort.

    /**
     * @brief Constructor de Monopoly.
     * @pre \p true
     * @post Crea Monopoly amb els atributs entrats.
     * @param read_board tauler llegit del JSONManager.
     * @param read_optional_actions accions opcionals llegides del JSONManager.
     * @param stack_of_cards pila de targetes sort del Monopoly.
     * @param initial_money diners inicials del Jugador.
     * @param start_rewards llista de recompenses per la casella de sortida "Start"
     */
    public Monopoly(Board read_board,ArrayList<optionalActions> read_optional_actions, ArrayList<Card> stack_of_cards,
                    int initial_money, ArrayList<String> start_rewards){
        this.board = read_board;
        this.optional_actions = read_optional_actions;
        this.cards = stack_of_cards;
        this.initial_money = initial_money;
        this.start_rewards = start_rewards;
    }


    /**
     * @brief Mètode que s'encarrega de gestionar els fluxe de joc del Monopoly (comprovar si s'ha de seguir, jugar,
     * finalitzar).
     * @pre true
     * @post Es jugara al Monopoly fins que s'hagi acabat el joc.
     */
    public void play(){
        startGame();

        while(!checkEndGame()) {
            current_player = players.get(current_player_iterator);
            //System.out.println(board.toString());
            System.out.println("---------- TORN DEL JUGADOR: " + current_player.getName() + " ----------");
            String temp = current_player.toString();
            System.out.println("-----------------------------------------------------------------------\n");
            if (!current_player.getBankruptcy()) {
                throwDice();
                movePlayer();
                Box current = getCurrentBox();
                Movement aux = new Movement(current, current_player, players, board, start_rewards, cards);
                String current_box_type = current.getType();
                System.out.println(board.toString());
                current_player.payLoans(board,aux);
                System.out.println("-----------------------------------------------------------------------\n");
                switch (current_box_type) {
                    case "START":
                        aux.startAction();
                        break;
                    case "FIELD":
                        aux.fieldAction();
                        break;
                    case "BET":
                        aux.betAction();
                        break;
                    case "LUCK":
                        aux.luckAction();
                        break;
                    case "DIRECTCOMMAND":
                        aux.directComand();
                        break;
                    case "EMPTY":
                        System.out.println("Ha caigut en una casella buida que no realitza cap acció\n");
                        break;
                    default:
                        // ERROR
                        break;
                }
                System.out.println("\n-----------------------------------------------------------------------\n");
                if (!current_player.getBankruptcy()) {
                    System.out.println("AVANS DE FINALIZTAR EL TORN, POT FER UNA DE LES SEGUENTS ACCIONS OPCIONALS:");
                    aux.optionalActions(optional_actions);
                    System.out.println("-----------------------------------------------------------------------\n");
                }
            }
            else {
                System.out.println("ERROR: Jugador en Fallida, per tant no pot jugar el seu torn");
            }
            if (current_player.getFields().size() > 10 || current_player.getMoney() > 100000) {
                System.out.println("SOMETHING WENT WRONG");
            }
            nextPlayer();

        }
        endGame();

    }

    public void setCards(ArrayList<Card> read_cards){
        cards = read_cards;
    }

    /**
     * @brief Inicialitza l'accio de moure Jugador i dona el resultat dels daus.
     * @pre true
     * @post Jugador sera mogut fins a \p position del  \board de Monopoly.
     */
    private void movePlayer(){
        int position = dice_result.getKey()+dice_result.getValue();
        if (current_player.getPosition()+position > board.getSize()-1) {
            position = current_player.getPosition() + position - board.getSize()+1;
        }
        else position = current_player.getPosition() + position;
        System.out.println("Et mous de la posicio " + current_player.getPosition() + " a la posicio " + position + "\n");
        board.movePlayer(current_player,position,start_rewards);
    }

    /**
     * @brief Retornara la casella actual del Jugador.
     * @pre true
     * @post Casella actual on esta el Jugador retornada.
     * @return \p box on es el Jugador.
     */
    private Box getCurrentBox() {
        return board.getBox(this.current_player);
    }

    /**
     * @brief Comproba si el joc s'ha acabat o no.
     * @pre true
     * @post Retorna \p true si el joc s'ha acabat \p false altrament.
     * @return \p true si s'ha acabat, \p false altrament.
     */
    private Boolean checkEndGame() {
        boolean end = true;
        for (Player aux : players) {
            if (!aux.getBankruptcy()) end = false;
        }
        return end;
    }

    /**
     * @brief Dona el numero de jugadors que no estan en fallida jugant a Monopoly.
     * @pre true
     * @post Retorna el nombre de jugadors amb \p bankruptcy = false.
     * @return enter de jugadors \p bakruptcy = false.
     */
    private int activePlayers() {
        int count = 0;
        for (Player aux : players) {
            if (!aux.getBankruptcy()) count++;
        }
        return count;
    }

    /**
     * @brief Salta al següent Jugador.
     * @pre \p true
     * @post S'ha passat al segÜent Jugador de la llista.
     */
    private void nextPlayer() {
        System.out.println("---------- TORN FINALITZAT ----------");
        current_player_iterator++;
        if (current_player_iterator ==players.size()) current_player_iterator = 0;
        while (players.get(current_player_iterator).getBankruptcy()) {
            current_player_iterator++;
            if (current_player_iterator ==players.size()) current_player_iterator = 0;
        }
        System.out.println("\n---------- SEGUENT JUGADOR ----------\n");

    }

    /**
     * @brief Tira els daus d'un torn.
     * @pre \p true
     * @post Dona el resultat dels daus d'aquest torn.
     */
    private void throwDice() {
        Random rand = new Random();
        int first_dice = rand.nextInt(5) + 1;
        int second_dice = rand.nextInt(5) + 1;
        Pair<Integer,Integer> aux = new Pair<Integer,Integer>(first_dice,second_dice);
        dice_result = aux;
        if (current_player.getType()=="USER") {
            System.out.println("Enter per tirar els daus");
            String readString = scan.nextLine();
        }
        System.out.println("RESULTAT DELS DAUS: " + first_dice + " | " + second_dice);
    }

    /**
     * @brief Inicialitza el joc del Monopoly entrant el nombre de Jugadors i el seu nom.
     * @pre \p true
     * @post Els jugadors han sigut entrats amb el seu nom.
     */
    private void startGame() {
        System.out.println("JOC DE MONOPOLY INICIAT");
        System.out.println("ENTRA EL NUMERO DE JUGADORS:");
        int number_of_players = scan.nextInt();
        for (int i=0;i<number_of_players;i++) {
            System.out.println("ENTRA EL NOM DEL SEGUENT JUGADOR");
            String name = scan.next();
            System.out.println("El jugador es un usuari o una CPU:");
            System.out.println("0. Usuari");
            System.out.println("1. CPU");
            System.out.println("Seleccioni la opcio que desitgi:");
            int value = scan.nextInt();
            if (value == 0) {
                Player aux = new TerminalPlayer(name,initial_money,0);
                players.add(aux);
                board.addPlayer(aux);
            }
            else if (value == 1){
                Player aux = new CPUPlayer(name,initial_money,0);
                players.add(aux);
                board.addPlayer(aux);
            }
            else System.out.println("ERROR!!!!!");
        }
    }

    /**
     * @brief Gestiona la finalització del joc de Monopoly.
     * @pre \p true
     * @post Finalitza el joc i mostra per pantalla el resum de la partida.
     */
    private void endGame(){
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (!player.getBankruptcy()) winners.add(player);
        }
        if (winners.size() == 1) {
            System.out.println(winners.get(0).getName() + "Ha guanyat la partida");
        }
        else if (winners.size() > 1){
            Player winner = winners.get(0);
            for (Player player : winners) {
                if (player.getMoney() > winner.getMoney()) {
                    winners.remove(player);
                }
                else if (player.getMoney() > winner.getMoney()) {
                    winners.remove(winner);
                    winner = player;
                }
            }
            if (winners.size()>1) {
                System.out.println("Hi ha hagut un empat entre:");
                for (Player player : winners) System.out.println(player.toString());
            }
            else {
                System.out.println(winners.get(0).getName() + "Ha guanyat la partida");
            }
        }
        else ;// Throw error
        System.out.println("\nRESUM FINAL DELS JUGADORS:");
        printPlayers(false);
    }

    /**
     * @brief Mostra els jugadors que no estan en fallida jugant a Monopoly.
     * @pre \p true
     * @post Retorna els jugadors amb \p bankruptcy = false.
     */
    private void printPlayers (boolean active) {
        if (active) System.out.println("JUGADORS ACTIUS");
        else System.out.println("JUGADORS");
        for ( Player aux : players) {
            if (active) {
                if (!aux.getBankruptcy()) System.out.println(aux.toString());
            }
            else {
                System.out.println(aux.toString());
            }
        }
    }
}

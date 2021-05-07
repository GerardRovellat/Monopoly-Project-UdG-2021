import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Random;
import javafx.util.Pair;

import javax.swing.text.StyledEditorKit;


public class Monopoly {
    private ArrayList<Player> players = new ArrayList<>();
    private Board board;

    private int initial_money;
    private ArrayList<String> start_rewards = new ArrayList<>();

    private Pair<Integer,Integer> dice_result;

    private Iterator<Player> players_iterator = players.iterator();
    private Player actual_player;

    private ArrayList<optionalActions> optional_actions;
    private ArrayList<Card> cards;

    /**
     * @brief $$$$
     * @pre true
     * @post Create Monopoly with the input attributes
     */
    public Monopoly(Board read_board,ArrayList<optionalActions> read_optional_actions, ArrayList<Card> stack_of_cards, int initial_money, ArrayList<String> start_rewards){
        this.board = read_board;
        this.optional_actions = read_optional_actions;
        this.cards = stack_of_cards;
        this.initial_money = initial_money;
        this.start_rewards = start_rewards;
    }


    /**
     * @brief $$$$
     * @pre true
     * @post General that manage the flow of the game turns
     */

    public void play(){
        startGame();

        while(!checkEndGame()) {
            actual_player = players_iterator.next();
            throwDice();
            movePlayer();
            Box actual = getActualBox();
            Movement aux = new Movement(actual,actual_player,players,board,start_rewards,cards);
            String actual_box_type = actual.getType();

            switch (actual_box_type) {
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
                    System.out.println("EMPTY BOX");
                    break;
                default:
                    // ERROR
                    break;
            }
            endTurn();

        }
        endGame();

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the number of boxes that player have to cross
     */

    private void movePlayer(){
        board.movePlayer(actual_player,dice_result.getKey()+dice_result.getValue(),start_rewards);
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the actual Box
     */
    private Box getActualBox() {
        return board.getBox(this.actual_player);
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns TRUE if the game its end FALSE otherwise
     */
    private Boolean checkEndGame() {
        boolean end = true;
        for (Player aux : players) {
            if (!aux.getBankruptcy()) end = false;
        }
        return end;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the number of players without bankruptcy
     */
    private int activePlayers() {
        int count = 0;
        for (Player aux : players) {
            if (!aux.getBankruptcy()) count++;
        }
        return count;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Do the final possible actions in a turn and select the next player
     */
    private void endTurn() {

        System.out.println("TORN FINALITZAT");
        while (!actual_player.getBankruptcy()) {
            actual_player = players_iterator.next();
        }

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the dice result
     */
    private void throwDice() {
        Random rand = new Random();
        int first_dice = rand.nextInt(5) + 1;
        int second_dice = rand.nextInt(5) + 1;
        Pair<Integer,Integer> aux = new Pair<Integer,Integer>(first_dice,second_dice);
        dice_result = aux;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$
     */
    private void startGame() {
        System.out.println("JOC DE MONOPOLY INICIAT");
        System.out.println("ENTRA EL NUMERO DE JUGADORS:");
        Scanner scan = new Scanner(System.in);
        int number_of_players = scan.nextInt();
        System.out.println("ENTRA EL NOM DE CADA JUGADOR");
        for (int i=0;i<number_of_players;i++) {
            String name = scan.nextLine();
            Player aux = new Player(name,initial_money,0);
            players.add(aux);
        }
    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$
     */
    private void endGame(){
        ArrayList<Player> winers = new ArrayList<>();
        for (Player player : players) {
            if (!player.getBankruptcy()) winers.add(player);
        }
        if (winers.size() == 1) {
            System.out.println(winers.get(0).getName() + "Ha guanyat la partida");
        }
        else if (winers.size() > 1){
            Player winer = winers.get(0);
            for (Player player : winers) {
                if (player.getMoney() > winer.getMoney()) {
                    winers.remove(player);
                }
                else if (player.getMoney() > winer.getMoney()) {
                    winers.remove(winer);
                    winer = player;
                }
            }
            if (winers.size()>1) {
                System.out.println("Hi ha hagut un empat entre:");
                for (Player player : winers) System.out.println(player.toString());
            }
            else {
                System.out.println(winers.get(0).getName() + "Ha guanyat la partida");
            }
        }
        else ;// Throw error
        System.out.println("\nRESUM FINAL DELS JUGADORS:");
        printPlayers(false);
    }


    public void setCards(ArrayList<Card> read_cards){
        cards = read_cards;
    }

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

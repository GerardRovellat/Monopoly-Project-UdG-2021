import java.util.ArrayList;
import java.util.Stack;
import javafx.util.Pair;


public class Monopoly {
    private ArrayList<Player> players;
    private Board board;
    private int initial_money;
    private Pair<Integer,Integer> dice_result;
    private int player_iterator = 0;
    private Player actual_player = players.get(player_iterator);
    private ArrayList<optionalActions> optional_actions;
    private Stack<Card> cards;

    /**
     * @brief $$$$
     * @pre true
     * @post Create Monopoly with the input attributes
     */
    public Monopoly(Board read_board,ArrayList<optionalActions> read_optional_actions){
        board = read_board;
        optional_actions = read_optional_actions;
    }

    public void setInitialMoney(int money){
        initial_money = money;
    }



    /**
     * @brief $$$$
     * @pre true
     * @post General that manage the flow of the game turns
     */
    /*
    public void play(){
        /*
        	startGame();

            while(not checkEndGame) {
                throwDice();
                movePlayer();
                Box actual = getActualBox;
                Movement aux(player,actual);
                switch:
                    case:
                    case:
                    case:
                    case:
                    case:
                end:
                endTurn();

	        }

            endGame();
         */
    //}

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the number of boxes that player have to cross
     */
    /*
    private void movePlayer(){

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the actual Box
     */
    /*private Box getActualBox{

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post Returns TRUE if the game its end FALSE otherwise
     */
    /*private Boolean checkEndGame{

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the number of players without bankruptcy
     */
    /*private int activePlayers{

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post Do the final possible actions in a turn and select the next player
     */
    /*private void endTurn{

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the dice result
     */
    /*private void throwDice{

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$
     */
    /*private void startGame{

    }*/

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$
     */
    /*private void endGame(){

    }*/


    public void setCards(Stack<Card> read_cards){
        cards = read_cards;
    }
}

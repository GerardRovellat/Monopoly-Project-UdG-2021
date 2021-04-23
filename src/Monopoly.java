import java.util.ArrayList;

import javafx.util.Pair;


public class Monopoly {
    private ArrayList<Player> players;
    private Board board;

    private Pair<Integer,Integer> dice_result;
    //private int player_iterator = 0;
    //private Player player = players.get(player_iterator); //??
    private ArrayList<optionalActions> optionalActions;

    /**
     * @brief $$$$
     * @pre true
     * @post Create Monopoly with the input attributes
     */
    public Monopoly(Board board,ArrayList<optionalActions> optionalActions){

    }

    /**
     * @brief $$$$
     * @pre true
     * @post General that manage the flow of the game turns
     */
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
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the number of boxes that player have to cross
     */
    private void movePlayer(){

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the actual Box
     */
    private Box getActualBox{

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns TRUE if the game its end FALSE otherwise
     */
    private Boolean checkEndGame{

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the number of players without bankruptcy
     */
    private int activePlayers{

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Do the final possible actions in a turn and select the next player
     */
    private void endTurn{

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the dice result
     */
    private void throwDice{

    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$
     */
    private void startGame{

    }

    /**
     * @brief $$$$
     * @pre true
     * @post $$$$$
     */
    private void endGame(){

    }
}

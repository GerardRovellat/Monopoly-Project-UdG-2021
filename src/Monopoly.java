import java.util.ArrayList;

import javafx.util.Pair;
/* Struct boxInformation{
    Box name;
    ArrayList players;
    bool bought;
    Player owner;
}
Map <int,struct> Tauler;
*/
//?? Guardar la info de la casella dins la classe casella ( o crear una nova ) per aixis poder recorrer i canviar coses mes rapid sense haver de tocar internament els parametres de cada classe.


public class Monopoly {
    private Pair<Integer,Integer> dice; //??
    //private Board board;  //?? Millor crear classe amb la info de sobre o fer-ho directament a Casella

    /**
     * @brief $$$$
     * @pre true
     * @post Create Monopoly with the input attributes
     */
    public Monopoly(ArrayList<Player> player_list){

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
    private int movePlayer(){

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
    private Pair<Integer,Integer>  throwDice{

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

import java.util.ArrayList;

public class Movement {

    private Box actual_position;
    private Player active_player;


    /**
     * @brief Movement class Constructor
     * @pre \p actual_posicion and \p active_player valid
     * @post Create a movement with box and player
     * @param box "Casella" position of actual_player on the board
     * @param player Information of the player playing at this torn
     */
    public void Movement(Box box,Player player){

    }

    /**
     * @brief $$$$$$$
     * @pre true
     * @post Gives the reward when the player cross or falls into the start box
     */
    public void startAction() {

    }


    /**
     * @brief Actions if the player falls into a field box
     * @pre true
     * @post Manages movement when player falls into the property box
     */
    public void fieldAction(){
        /*
        comprovar titularitat del terreny;
		si esta lliure {
			Pregunta al usuari quina accio vol fer amb aquest terreny ( comprar o no );
			si el compra {
				Canvia el estat del terreny;
				Resta al jugador el que costa el terreny
			}
		}
		si es de un jugador {
			consultar lloguer del terreny;
			Jugador temporal = Retorna al jugador propietari();
			resta el lloguer al jugador actual;
			l'hi suma el lloguer al jugador propietari (temporal);
		}
		si es seva {
			Consultar si vol edificar;
			Preguntar quantitat per edificar;

			si vol {
				si es edificable() { edifica }
			}
		}
        */
    }


    /**
     * @brief Actions if the player falls into a Bet box
     * @pre true
     * @post Gives the amount of the bet to the player that is doing the movement
     */
    public void betAction(){
        /*
            tira els daus;
            modifica diners jugador segons resulat aposta;
         */
    }

    /**
     * @brief Actions if the player falls into a DirectComand box
     * @pre true
     * @post Does the movement depending of the type of direct order it is
     */
    public void directComand(){
        /*
            Comprova quin tipus de comandaDirecte es();
                switch:
                    case1: multa: executarTargeta(multa);
                    case2: ...
                end:
         */
    }


    /**
     * @brief Actions depending of the card got
     * @pre true
     * @post SOMETHING
     */
    public void runCard(Card top_card){
        /*
            Executa accions targeta
         */
    }

    /*------------OPCIONALS ACTIONS---------------*/

    /**
     * @brief Manage all posible optional Actions
     * @pre true
     * @post SOMETHING
     */
    public void optionalActions(ArrayList<optionalActions> possible_actions){
        /*
            Pregunta al usuari aqueste accions;
            posiblesAccions(entradaUsuari).executar(llista Jugadors,);
         */
        //?? L'hi podem passar la interface o l'hi passem un index (int, String)

    }
}
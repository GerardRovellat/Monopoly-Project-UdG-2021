public class Actions implements Comparable<Actions> {

    private Box actual_position;
    private Player active_player;

    /**
     * @brief Actions class Constructor
     * @pre \p actual_posicion and \p active_player valid
     * @post Guarda l'informació de la tirada
     * @param actual_position "Casella" position of actual_player on the board
     * @param active_player Information of the player playing at this torn
     */
    public Actions(Box actual_position,Player active_player){

    }


    /**
     * @brief Actions if the player falls into a field box
     * @pre true
     * @post SOMETHING
     */
    public field(){
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
     * @post SOMETHING
     */
    public bet(){
        /*
            tira els daus;
            modifica diners jugador segons resulat aposta;
         */
    }

    /**
     * @brief Actions if the player falls into a DirectComand box
     * @pre true
     * @post SOMETHING
     */
    public directComand(){
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
    public executeCard(Cart active){
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
    public optionalActions(ArrayList<posibleActions> optional_actions_list){
        /*
            Pregunta al usuari aqueste accions;
            posiblesAccions(entradaUsuari).executar(llista Jugadors,);
         */
    }
}
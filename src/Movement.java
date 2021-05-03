import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Movement {

    private Box actual_box;
    private Player active_player;
    private HashMap<Integer,String> user_actions;


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
        Scanner scan = new Scanner(System.in);
        Field field = (Field) actual_box;
        if (field.isBought()) {
            if (field.getOwner()==active_player) {
                System.out.println("-  La casella de terreny on ha caigut es de la seva propietat  -");
                System.out.println("Accions displonibles:");
                printUserActions(new int[] {0,4});
                int action = scan.nextInt();
                switch (action) {
                    case 0:
                        System.out.println("Accio selecionada: 0. Res");
                        break;
                    case 1: // BUILD
                        System.out.println("Accio selecionada: 1. Edificar");
                        printUserActions(new int[] {0,5,6});        // Posible build accions ( apartament, hotel, nothing )
                        for (boolean optionFin = false;!optionFin;) {
                            int option = scan.nextInt();
                            if (option == 5 || option == 6 || option == 0) {
                                optionFin = true;
                                if (option == 5 && field.houseBuildable()) {        // true i apartament selected and it is posible to build
                                    int price_to_build = field.priceToBuild();      // Get the price to build one apartament
                                    int numberOfHouseBuildable = field.numberOfHouseBuildable();        // get the maximum number of apartament the player is able to build
                                    System.out.println("Es pot construir fins a " + numberOfHouseBuildable + "apartaments a un preu de " + price_to_build + "€ per apartament");
                                    if (active_player.isAffordable(price_to_build) > 0) {       // true if the player can aford at least one apartament
                                        System.out.println("Actualment disposa de " + active_player.getMoney() + "€ que l'hi permet comprar fins a " + active_player.isAffordable(price_to_build) + " apartaments");
                                        int quantity = 0;
                                        for (boolean second_end = false; !second_end; ) {         // Check if it's posible to build quantity apartaments
                                            System.out.println("Quina quantitat de apartaments vol edificar? Introdueixi la quantitat: ");
                                            quantity = scan.nextInt();      // Get number of apartaments to build
                                            if (quantity <= active_player.isAffordable(price_to_build))
                                                second_end = true;      // Check if posible to build that many apartaments
                                            else
                                                System.out.println("Es imposible edificar " + quantity + "apartaments");
                                        }
                                        int final_price = quantity * price_to_build;        // Calculate final price
                                        System.out.println("S'edificaràn " + quantity + "apartaments a un preu total de " + final_price + "€" + "i l'hi quedaràn " + (active_player.getMoney() - final_price) + "€");
                                        printUserActions(new int[]{1, 2});      // Actions confirmation actions ( confirmate, denegate )
                                        for (boolean fin = false; !fin; ) {       // Check if actions value is correct
                                            int value = scan.nextInt();         // Get action number
                                            if (value == 1 || value == 2)
                                                fin = true;       // Check if actions value is correct
                                            if (value == 2) System.out.println("Operacio cancelada");
                                            else if (value == 1) {
                                                field.build(quantity);          // Build x apartaments (x = quantity)
                                                active_player.pay(final_price);         // Modify active player money ( money - final price )
                                                System.out.println("Operacio realitzada");
                                                // Treure resum final del jugador
                                            } else System.out.println("Valor entrat erroni, torni a provar");
                                        }
                                    } else System.out.println("No te suficients diners per construir cap apartament");
                                } else System.out.println("No te cap apartament per construir");
                                if (option == 6 && field.hotelBuildable()) {
                                    int price_to_build = field.priceToBuild();
                                    System.out.println("Es pot construir un hotel a un preu de " + price_to_build + "€");
                                    if (active_player.getMoney() >= price_to_build) {
                                        System.out.println("Actualment disposa de " + active_player.getMoney() + "€");
                                        System.out.println("S'edificarà un hotel a un preu total de " + price_to_build + "€" + "i l'hi quedaràn " + (active_player.getMoney() - price_to_build) + "€");
                                        printUserActions(new int[]{1, 2});      // Actions confirmation actions ( confirmate, denegate )
                                        for (boolean fin = false; !fin; ) {       // Check if actions value is correct
                                            int value = scan.nextInt();         // Get action number
                                            if (value == 1 || value == 2)
                                                fin = true;       // Check if actions value is correct
                                            if (value == 2) System.out.println("Operacio cancelada");
                                            else if (value == 1) {
                                                field.build(1);          // Build the hotel (quantity = 1)
                                                active_player.pay(price_to_build);         // Modify active player money ( money - hotel price )
                                                System.out.println("Operacio realitzada");
                                                // Treure resum final del jugador
                                            } else System.out.println("Valor entrat erroni, torni a provar");
                                        }
                                    } else System.out.println("No te suficients diners per construir el hotel");
                                } else
                                    System.out.println("No te cap hotel per construir o encara no ha construit tots els apartaments");
                            }
                            else System.out.println("Valor entrat erroni, torni a provar");
                        }
                        System.out.println("FINAL EDIFICAR");
                        break;
                    default:
                        break;
                }
                System.out.println("FINAL ACCIONS DISPONIBLES");
            }
            else {
                // CASELLA COMPRADA PERO DE UN ALTRE JUGADOR
                Player owner = field.getOwner();
                System.out.println("-  Ha caigut en una casella ja comprada -");
                System.out.println("El propietari del terreny es "  + owner.getName() +  " i el lloguer es de " + field.getRent() + "€");
                if (active_player.getMoney() >= field.getRent()) {
                    active_player.pay(field.getRent());
                    owner.charge(field.getRent());
                    System.out.println("El lloguer s'ha pagat");
                }
                else {/*JUGADOR ELIMINAT*/}
            }
        }
        else {
                // CASELLA NO COMPRADA ENCARA
        }


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
    public void runCard(Card card){
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

    }

    /**
     * @brief $$$$$$
     * @pre true
     * @post SOMETHING
     */
    public void userActionsStart(){
        user_actions.put(0,"Res");
        user_actions.put(1,"Confirmar");
        user_actions.put(2,"Anular");
        user_actions.put(3,"Comprar");
        user_actions.put(4,"Edificar");
        user_actions.put(5,"Apartaments");
        user_actions.put(6,"Hotel");
    }


    /**
     * @brief $$$$$$
     * @pre true
     * @post SOMETHING
     */
    public void printUserActions(int[] actions){
        user_actions.put(0,"Res");
        for (Integer aux : actions ) {
            System.out.println(aux + ". " + user_actions.get(aux));
        }
        System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
    }

}
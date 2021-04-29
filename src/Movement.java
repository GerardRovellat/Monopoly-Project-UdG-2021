import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Scanner;

public class Movement {

    private Box actual_box;
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
        Scanner scan = new Scanner(System.in);
        Field field = (Field) actual_box;
        if (field.isBought()) {
            if (field.getOwner()==active_player) {
                System.out.println("-  La casella de terreny on ha caigut es de la seva propietat  -");
                System.out.println("Accions displonibles:");
                System.out.println("0. Res");
                System.out.println("1. Edificar");
                System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                int action = scan.nextInt();
                switch (action) {
                    case 0:
                        System.out.println("Accio selecionada: 0. Res");
                        break;
                    case 1:
                        System.out.println("Accio selecionada: 1. Edificar");
                        boolean end = false;
                        while (!end) {
                            if (field.houseBuildable()) {
                                int price_to_build = field.priceToBuild();
                                int numberOfHouseBuildable = field.numberOfHouseBuildable();
                                System.out.println("Es pot construir fins a " + numberOfHouseBuildable + "apartaments a un preu de " + price_to_build + "€ per apartament");
                                if (active_player.isAffordable(price_to_build) > 0) {
                                    System.out.println("Actualment disposa de " + active_player.getMoney() + "€ que l'hi permet comprar fins a " + active_player.isAffordable(price_to_build) + " apartaments");
                                    int quantity = 0;
                                    for (boolean second_end = false;!second_end;) {
                                        System.out.println("Quina quantitat de apartaments vol edificar? Introdueixi la quantitat: ");
                                        quantity = scan.nextInt();
                                        if (quantity <= active_player.isAffordable(price_to_build)) second_end = true;
                                        else System.out.println("Es imposible edificar " + quantity + "apartaments");
                                    }
                                    int final_price = quantity * price_to_build;
                                    System.out.println("S'edificaràn " + quantity + "apartaments a un preu total de " + final_price + "€" + "i l'hi quedaràn " + (active_player.getMoney() - final_price) + "€" );
                                    System.out.println("0. Anular");
                                    System.out.println("1. Confirmar");
                                    System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                                    int value = scan.nextInt();
                                    if (value == 0) System.out.println("Operacio cancelada");
                                    else {
                                        field.build(quantity);
                                        active_player.pay(final_price);
                                        System.out.println("Operacio realitzada");
                                        // Treure resum final del jugador
                                    }
                                }
                                else System.out.println("No te suficients diners per construir cap apartament");

                            } else if (field.hotelBuildable()) {
                                }
                            }
                        }
                        System.out.println("FINAL EDIFICAR");

                        break;
                    default:
                        break;
                }
                System.out.println("FINAL ACCIONS DISPONIBLES");
            }
        }
        else {

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
}
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.*;

public class Movement {

    private Box actual_box;
    private Player active_player;
    private HashMap<Integer,String> user_actions = new HashMap<>();
    ArrayList<Player> players;
    Board board;
    private ArrayList<String> start_rewards;
    private ArrayList<Card> cards;




    /**
     * @brief Movement class Constructor
     * @pre \p actual_posicion and \p active_player valid
     * @post Create a movement with box and player
     * @param box "Casella" position of actual_player on the board
     * @param player Information of the player playing at this torn
     */
    public Movement(Box box,Player player,ArrayList<Player> players,Board board,ArrayList<String> start_rewards,ArrayList<Card> cards){
        this.actual_box = box;
        this.active_player = player;
        this.players = players;
        this.board = board;
        this.start_rewards = start_rewards;
        this.cards = cards;
        userActionsStart();
    }

    /**
     * @brief $$$$$$$
     * @pre true
     * @post Gives the reward when the player cross or falls into the start box
     */
    public void startAction() {
        System.out.println("Has caigut a la casella de Inici i rebut la recompensa");
    }


    /**
     * @brief Actions if the player falls into a field box
     * @pre true
     * @post Manages movement when player falls into the property box
     */
    public void fieldAction() {
        Scanner scan = new Scanner(System.in);
        Field field = (Field) actual_box;
        if (field.isBought()) {
            if (field.getOwner() == active_player) {
                System.out.println("-  La casella de terreny on ha caigut es de la seva propietat  -");
                printUserActions(new int[]{0, 4});
                int action = scan.nextInt();
                switch (action) {
                    case 0:
                        System.out.println("Accio selecionada: 0. Res");
                        break;
                    case 4: // BUILD
                        System.out.println("Accio selecionada: 1. Edificar");
                        printUserActions(new int[]{0, 5, 6});        // Posible build accions ( apartament, hotel, nothing )
                        for (boolean optionFin = false; !optionFin; ) {
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
                                        for (boolean end = false; !end; ) {         // Check if it's posible to build quantity apartaments
                                            System.out.println("Quina quantitat de apartaments vol edificar? Introdueixi la quantitat: ");
                                            quantity = scan.nextInt();      // Get number of apartaments to build
                                            if (quantity <= active_player.isAffordable(price_to_build))
                                                end = true;      // Check if posible to build that many apartaments
                                            else
                                                System.out.println("Es imposible edificar " + quantity + "apartaments");
                                        }
                                        int final_price = quantity * price_to_build;        // Calculate final price
                                        System.out.println("S'edificaràn " + quantity + "apartaments a un preu total de " + final_price + "€" + "i l'hi quedaràn " + (active_player.getMoney() - final_price) + "€");
                                        printUserActions(new int[]{1, 2});      // Actions confirmation actions ( confirmate, denegate )
                                        for (boolean end = false; !end; ) {       // Check if actions value is correct
                                            int value = scan.nextInt();         // Get action number
                                            if (value == 1 || value == 2)
                                                end = true;       // Check if actions value is correct
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
                                        for (boolean end = false; !end; ) {       // Check if actions value is correct
                                            int value = scan.nextInt();         // Get action number
                                            if (value == 1 || value == 2)
                                                end = true;       // Check if actions value is correct
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
                            } else System.out.println("Valor entrat erroni, torni a provar");
                        }
                        System.out.println("FINAL EDIFICAR");
                        break;
                    default:
                        break;
                }
                System.out.println("FINAL ACCIONS DISPONIBLES");
            } else {
                // CASELLA COMPRADA PERO DE UN ALTRE JUGADOR
                Player owner = field.getOwner();        // Get field owner
                System.out.println("-  La casella de terreny on ha caigut ja te propietari  -");
                System.out.println("El propietari del terreny es " + owner.getName() + " i el lloguer es de " + field.getRent() + "€");
                if (active_player.getMoney() >= field.getRent()) { // ture if player can pay rent
                    active_player.pay(field.getRent());     // player pay rent
                    owner.charge(field.getRent());          // owner get rent
                    System.out.println("El lloguer s'ha pagat");
                } else {/* JUGADOR ELIMINAT DE LA PARTIDA - BANCARROTA*/}
            }
        } else {
            System.out.println("-  La casella de terreny on ha caigut no te propietari  -");
            System.out.println(field.toString()); // Print field info ( name, price, rent, etc )
            printUserActions(new int[]{0, 3});      // Actions confirmation actions ( confirmate, denegate )
            for (boolean end = false; !end; ) {
                int value = scan.nextInt();
                if (value == 3) {
                    // Comprar
                    if (active_player.getMoney() >= field.getPrice()) {
                        System.out.println("Despres de la compra, et quedaràs amb " + (active_player.getMoney()- field.getPrice()) + "€");
                        printUserActions(new int[]{1,2});
                        int confirmation_value = -1;
                        while(confirmation_value != 1 && confirmation_value != 2) {
                            confirmation_value = scan.nextInt();
                            if (confirmation_value == 1) {
                                active_player.pay(field.getPrice());
                                active_player.addBox(field);
                                field.buy(active_player);
                                System.out.println("Casella comprada");
                            }
                            else System.out.println("Operació cancelada");
                        }
                    }
                    else System.out.println("No tens suficients diners per comprar");
                    end = true;
                } else if (value == 0) {
                    System.out.println("Casella no comprada");
                    end = true;
                } else System.out.println("Valor entrat erroni, torni a provar");
            }
        }
    }



    /**
     * @brief Actions if the player falls into a Bet box
     * @pre true
     * @post Gives the amount of the bet to the player that is doing the movement
     */
    public void betAction(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Entri la quantitat de la seva aposta");
        int quantity = -1;
        while (quantity < 0 || quantity > active_player.getMoney()) {
            quantity = scan.nextInt();
            if (quantity < 0 || quantity > active_player.getMoney()) {
                System.out.println("El valor que ha entrat no es correcte ( ha de estar entre 0 i " + active_player.getMoney() + " )");
                System.out.println("Torni a provar:");
            }
            else System.out.println("El valor entrat es correcte");
        }
        System.out.println("Entri el valor de la seva aposta");
        int bet = -1;
        while( bet < 2 || bet > 12) {
            bet = scan.nextInt();
            if (bet < 2 || bet > 12) {
                System.out.println("El valor que ha entrat no es correcte ( ha de estar entre 0 i 12 )");
                System.out.println("Torni a provar:");
            }
            else System.out.println("El valor entrat es correcte");
        }
        active_player.pay(quantity);
        System.out.println("Quantitat de aposta retirada del compte");
        Random rand = new Random();
        int first_dice = rand.nextInt(5) + 1;
        int second_dice = rand.nextInt(5) + 1;
        System.out.println("El resultat dels daus ha sigut " + first_dice + " i de " + second_dice);
        Bet aux = (Bet) actual_box;
        int result = aux.betResult(quantity,bet,first_dice+second_dice);
        if (result > 0) {
            System.out.println("Ha superat la aposta amb un resultat de +" + result + "€");
            active_player.charge(result);
            System.out.println("S'han afegit els fons al compte");
        }
        else {
            System.out.println("No ha superat la aposta");
        }
        System.out.println("Final aposta");
    }

    /**
     * @brief $$$$$
     * @pre true
     * @post $$$$$
     */
    public void luckAction(){
        System.out.println("Has caigut en una casella de sort");
        Card actual = cards.get(cards.size()-1);
        cards.remove(cards.size()-1);
        runCard(actual);
        cards.add(0,actual);
    }

    /**
     * @brief Actions if the player falls into a DirectComand box
     * @pre true
     * @post Does the movement depending of the type of direct order it is
     */
    public void directComand(){
        System.out.println("Has caigut en una casella de comanda directa");
        directComand actual = (directComand) actual_box;
        runCard(actual.getCard());
    }


    /**
     * @brief Actions depending of the card got
     * @pre true
     * @post SOMETHING
     */
    public void runCard(Card card){
        String type = card.getType();
        switch (type){
            case "CHARGE": // passsar a català
                CardCharge charge = (CardCharge) card;
                charge.execute(board,active_player);
                break;
            case "FINE":
                CardFine fine = (CardFine) card;
                fine.execute(board,active_player);
                break;
            case "GET":
                CardGet get = (CardGet) card;
                get.execute(players,board,active_player);
                break;
            case "GIVE":
                CardGive give = (CardGive) card;
                give.execute(players,board,active_player);
                break;
            case "GO":
                CardGo go = (CardGo) card;
                go.execute(players,board,active_player,start_rewards);
                break;
            case "PAY":
                CardPay pay = (CardPay) card;
                pay.execute(players,board,active_player);
                break;
            default:
                //trow error
                break;
        }
    }

    /*------------OPCIONALS ACTIONS---------------*/

    /**
     * @brief Manage all posible optional Actions
     * @pre true
     * @post SOMETHING
     */
    public void optionalActions(ArrayList<optionalActions> possible_actions){
        System.out.println("Accions Opcionals:");
        int index = 0;
        for (optionalActions aux : possible_actions) {
            System.out.println(index + " - " + aux.toString());
            index++;
        }
        int value = -1;
        while (value < 0 || value > index) {
            Scanner scan = new Scanner(System.in);
            value = scan.nextInt();
            if (value < 0 || value > index) System.out.println("El valor que ha entrat no es correcte ");
        }
        possible_actions.get(value).execute();
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
        System.out.println("Accions displonibles:");
        for (Integer aux : actions ) {
            System.out.println(aux + ". " + user_actions.get(aux));
        }
        System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
    }

}
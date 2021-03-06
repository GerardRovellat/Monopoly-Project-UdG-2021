import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marc Got
 * @file Movement.java
 * @class Movement
 * @brief Classe que s'encarrega de gestionar les accions que es poden fer en un moviment. Aquest moviment sera fet
 * sobre un Jugador.
 */
public class Movement {

    private final Box current_box;                                            ///< Casella actual.
    private final Player active_player;                                       ///< Jugador que esta realitzant el moviment.
    ArrayList<Player> players;                                                ///< Llista de Jugadors jugant.
    Board board;                                                              ///< Tale del Monopoly.
    private final ArrayList<String> start_rewards;                            ///< Llista de recompenses de la casella Sortida
    private final ArrayList<Card> cards;                                      ///< Llista de targetes sort de Monopoly
    private final OutputManager output;

    /**
     * @brief Constructor de Movement.
     * @pre \p current_posicion != null i \p active_player != null
     * @post Crea un moviment Movement amb \p box i \p player.
     * @param box Casella posició de \p active_player en el tauler.
     * @param player Informació de el Jugador que està jugant en aquest torn.
     */
    public Movement(Box box,Player player,ArrayList<Player> players,Board board,ArrayList<String> start_rewards,ArrayList<Card> cards,OutputManager output){
        this.current_box = box;
        this.active_player = player;
        this.players = players;
        this.board = board;
        this.start_rewards = start_rewards;
        this.cards = cards;
        this.output = output;
    }

    /**
     * @brief Informa al Jugador que ha caigut a la casella de sortida \p Start.
     * @pre \p true
     * @post La informació ha sigut mostrada per pantalla.
     */
    public void startAction() {
        output.fileWrite(active_player.getName() + "> Cau a Casella Sortida");
        System.out.println("Has caigut a la casella de Inici i rebut la recompensa");
    }


    /**
     * @brief Administra les accions que s'han de fer si es cau en una Casella de terreny \p Field.
     * @pre \p true
     * @post Les accions han sigut realitzades.
     */
    public void fieldAction() {
        BoxField field = (BoxField) current_box;
        output.fileWrite(active_player.getName() + "> Cau a Casella Terreny");
        if (field.isBought()) {
            if (field.getOwner() == active_player) build();
            else payRent();
        } else buyField();
    }

    /**
     * @brief Administra les accions que s'han de fer si es cau en una Casella d'aposta \p Bet.
     * @pre \p true
     * @post Dona la suma de diners de l'aposta que el jugador que fa el moviment ha realitzat.
     */
    public void betAction(){
        output.fileWrite(active_player.getName() + "> Cau a Casella Aposta");
        System.out.println("Cau a Casella Aposta");
        System.out.println("Entri la quantitat de la seva aposta");
        int quantity = -1;
        while (quantity < 0 || quantity > active_player.getMoney()) {
            quantity = active_player.optionSelection("betQuantity",null,null,null,null,null,0,null);
            if (quantity < 0 || quantity > active_player.getMoney()) {
                System.out.println("El valor que ha entrat no es correcte ( ha de estar entre 0 i " + active_player.getMoney() + " )");
                System.out.println("Torni a provar:");
            }
            else System.out.println("El valor entrat es correcte");
        }
        System.out.println("Entri el valor de la seva aposta");
        int bet = -1;
        while( bet < 3 || bet > 12) {
            bet = active_player.optionSelection("betValue",null,null,null,null,null,0,null);
            if (bet < 3 || bet > 12) {
                System.out.println("El valor que ha entrat no es correcte ( ha de estar entre 3 i 12 )");
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
        BoxBet aux = (BoxBet) current_box;
        int result = aux.betResult(quantity,bet,first_dice+second_dice);
        if (result > 0) {
            System.out.println("Ha superat la aposta amb un resultat de +" + result + "€");
            active_player.charge(result);
            System.out.println("S'han afegit els fons al compte");
            output.fileWrite(active_player.getName() + "> Aposta " + quantity + " a el valor " + bet + " i el resultat es " + (first_dice+second_dice)  + " i guanya " + (result-quantity));
        }
        else {
            System.out.println("No ha superat la aposta");
            output.fileWrite(active_player.getName() + "> Aposta " + quantity + " a el valor " + bet + " i el resultat es " + (first_dice+second_dice)  + " i perd " + (quantity-result-1));
        }
        System.out.println("Final aposta");
    }

    /**
     * @brief Administra les accions que s'han de fer si es cau en una Casella de sort.
     * @pre \p true
     * @post S'executarà la targeta de sort agafada de les targetes sort. En cas que la targeta sigui posposable pot
     * guardar-se o utilitzar-se, altrament s'executara.
     */
    public void luckAction(){
        output.fileWrite(active_player.getName() + "> Cau a Casella Sort");
        System.out.println("Has caigut en una casella de sort");
        Card current = cards.get(cards.size()-1);
        cards.remove(cards.size()-1);
        output.fileWrite(active_player.getName() + "> " +  current.toString());
        System.out.println(current.toString());
        if (current.isPostposable()) {
            System.out.println("Es pot guardar la carta per poderla utilitzar el qualsevol moment");
            System.out.println("0. Guardar per despres");
            System.out.println("1. fer-la servir ara");
            int value = -1;
            while (value < 0 || value > 1 ) {
                value = active_player.optionSelection("postposableLuckCardChoice",null,null,null,null,current,0,null);
                if (value < 0 || value > 1 ) System.out.println("Valor entrat incorrecte. Torni a provar:");
            }
            if (value == 0) {
                active_player.addLuckCard(current);
                System.out.println("Carta guardada.");
                output.fileWrite(active_player.getName() + "> Carta guardada" );
            }
            else {
                runCard(current);
                cards.add(0, current);
            }
        }
        else {
            runCard(current);
            cards.add(0, current);
        }
    }

    /**
     * @brief Administra les accions que s'han de fer si es cau en una Casella de comanda directa.
     * @pre \p true
     * @post Realitza el moviment depenent del tipus de comanda directa que és.
     */
    public void directComand(){
        output.fileWrite(active_player.getName() + "> Cau a Casella Comanda Directe");
        System.out.println("Has caigut en una casella de comanda directa");
        BoxDirectCommand current = (BoxDirectCommand) current_box;
        runCard(current.getCard());
    }


    /**
     * @brief Administra les accions que s'han de fer depenent de la targeta que et toqui.
     * @pre \p card != null
     * @post La targeta \p card ha estat executada.
     * @param card targeta que s'ha d'executar.
     */
    public void runCard(Card card){
        String type = card.getType();
        switch (type){
            case "CHARGE": // passsar a català
                CardCharge charge = (CardCharge) card;
                charge.execute(active_player);
                break;
            case "FINE":
                CardFine fine = (CardFine) card;
                fine.execute(board,active_player,this);
                break;
            case "GET":
                CardGet get = (CardGet) card;
                get.execute(players,active_player);
                break;
            case "GIVE":
                CardGive give = (CardGive) card;
                give.execute(players,active_player);
                break;
            case "GO":
                CardGo go = (CardGo) card;
                go.execute(board,active_player,start_rewards,output);
                break;
            case "PAY":
                CardPay pay = (CardPay) card;
                pay.execute(players,board,active_player,this);
                break;
        }
    }

    /**
     * @brief Administra totes les possibles accions opcionals.
     * @pre \p true
     * @post L'acció opcional ha estat executada.
     * @param possible_actions ArrayList amb totes les accions possibles que el Jugador pot fer.
     */
    public void optionalActions(ArrayList<optionalActions> possible_actions){
        int value;
        System.out.println("Accions Opcionals:");
        int index = 1;
        System.out.println("0 - RES");

        for (optionalActions aux : possible_actions) {
            System.out.println(index + " - " + aux.toString());
            index++;
        }
        value = active_player.optionSelection("optionalActionSelector",active_player,null,null,null,null,0,possible_actions);
        if (value != 0) {
            output.fileWrite(active_player.getName() + "> Accio opcional escollida: " +  value + " - " + possible_actions.get(value-1).getClass().getName());
            possible_actions.get(value - 1).execute(players, active_player, this);
        }
        else output.fileWrite(active_player.getName() + "> Accio opcional escollida: 0 - RES");

    }

    /**
     * @brief Administrarà la compra d'un terreny on s'ha caigut de tauler.
     * @pre true
     * @post Si el terreny no te propietari i el jugador ho desitja, el terreny serà comprat, altrament passarà.
     */
    public void buyField() {
        BoxField field = (BoxField) current_box;
        System.out.println("-  La casella de terreny on ha caigut no te propietari  -");
        System.out.println(field.toString()); // Print field info ( name, price, rent, etc )
        System.out.println("Accions displonibles:");
        System.out.println("0. Res");
        System.out.println("1. Comprar");
        System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
        for (boolean end = false; !end; ) {
            //int value = scan.nextInt();
            int value = active_player.optionSelection("buy",null,field,null,null,null,0,null);
            if (value == 1) {
                // Comprar
                if (active_player.getMoney() >= field.getPrice()) {
                    System.out.println("Despres de la compra, et quedaràs amb " + (active_player.getMoney()- field.getPrice()) + "€");
                    System.out.println("Accions displonibles:");
                    System.out.println("0. Confirmar");
                    System.out.println("1. Anular");
                    System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                    int confirmation_value = -1;
                    while(confirmation_value != 0 && confirmation_value != 1) {
                        confirmation_value = active_player.optionSelection("confirmation",active_player,field,null,null,null,0,null);
                        if (confirmation_value == 0) {
                            active_player.pay(field.getPrice());
                            active_player.addBox(field);
                            field.buy(active_player);
                            System.out.println("Casella comprada");
                            output.fileWrite(active_player.getName() + "> Compra " + field.getName() + " a un preu de " + field.getPrice());
                        }
                        else {
                            System.out.println("Operació cancelada");
                            output.fileWrite(active_player.getName() + "> No compra" + field.getName());
                        }
                    }
                }
                else {
                    System.out.println("No tens suficients diners per comprar");
                    output.fileWrite(active_player.getName() + "> No to deiners per comprar" + field.getName());
                }
                end = true;
            } else if (value == 0) {
                System.out.println("Casella no comprada");
                output.fileWrite(active_player.getName() + "> No compra" + field.getName());
                end = true;
            } else System.out.println("Valor entrat erroni, torni a provar");
        }
    }


    /**
     * @brief Administrarà el pagament dels lloguers en els terrenys amb propietaris
     * @pre true
     * @post El jugador ha pagat el lloguer del terreny on ha caigut al seu Jugador propietari.
     */
    public void payRent() {
        BoxField field = (BoxField) current_box;
        Player owner = field.getOwner();                                // Retorna propietari terreny
        System.out.println("-  La casella de terreny on ha caigut ja te propietari  -");
        System.out.println("El propietari del terreny es " + owner.getName() + " i el lloguer es de " + field.getRent() + "€");
        if (active_player.getMoney() >= field.getRent()) {              // True si jugador pot pagar el lloguer
            active_player.pay(field.getRent());                         // Jugador paga el lloguer
            owner.charge(field.getRent());                              // Propietari rep el lloguer
            System.out.println("El lloguer s'ha pagat");
            output.fileWrite(active_player.getName() + "> Paga el lloguer de " + field.getName() + "( " + field.getRent() + "€ ) a " + owner.getName());
        } else {
            output.fileWrite(active_player.getName() + "> No pot pagar el lloguer de " + field.getName() + "( " + field.getRent() + "€ ) a " + owner.getName());
            if (board.isBankrupt(active_player,field.getRent(),this)) {
                board.transferProperties(active_player,field.getOwner(),this);
                active_player.goToBankruptcy();
            }
            else {payRent();}
        }
    }

    /**
     * @brief Administra l'acció per contruïr edificis si el terreny es de la propietat de Jugador.
     * @pre true
     * @post L'acció per contruïr edificis dins d'una propietat ha estat gestionada.
     */
    public void build() {
        BoxField field = (BoxField) current_box;
        System.out.println("-  La casella de terreny on ha caigut es de la seva propietat  -");
        if (field.houseBuildableType().equals("si") || field.houseBuildableType().equals("agrupacio")) {
            if ( (field.houseBuildableType().equals("agrupacio") && board.numberOfAgrupationField(field.getGroup()) == active_player.numberOfAgrupationField(field.getGroup())) || field.houseBuildableType().equals("si")) {
                System.out.println("Accions displonibles:");
                System.out.println("0. Res");
                System.out.println("1. Edificar");
                System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                int action = active_player.optionSelection("buildChoice",null,field,null,null,null,0,null);
                switch (action) {
                    case 0: // NOTHING
                        System.out.println("Accio selecionada: 0. Res");
                        break;
                    case 1: // BUILD
                        System.out.println("Accio selecionada: 1. Edificar");
                        for (int option = -1; option != 0; ) {
                            System.out.println("Accions displonibles:");
                            System.out.println("0. Res");
                            System.out.println("1. Apartaments");
                            System.out.println("2. Hotel");
                            System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                            option = active_player.optionSelection("build",null,field,null,null,null,0,null);
                            if (option == 0) System.out.println("FINAL DE EDIFICACIÓ");
                            else if (option == 1)  buildApartament(field);
                            else if (option == 2)  buildHotel(field);
                            else System.out.println("Valor entrat erroni, torni a provar");
                        }
                        System.out.println("FINAL EDIFICAR");
                        break;
                    default:
                        break;
                }
                System.out.println("FINAL ACCIONS DISPONIBLES");
            }
            else System.out.println("Encara no tens totes les propietats de la agrupació i per tant no pots edificar");
        }
        else System.out.println("No es posible edificar en aquest terreny");
    }

    /**
     * @brief Administra l'acció per contruïr apartaments en una propietat.
     * @pre \p field != null
     * @post L'acció per contruïr apartaments dins d'una propietat ha estat gestionada.
     * @param field terreny on s'ha de contruïr apartaments.
     */
    private void buildApartament(BoxField field) {
        int price_to_build = field.priceToBuild();      // Get the price to build one apartament
        int numberOfHouseBuildable = field.numberOfHouseBuildable();        // get the maximum number of apartament the player is able to build
        System.out.println(field.toString());
        System.out.println("Apartaments Construits: " + field.getNumberOfApartaments());
        System.out.println("Hotels Construits: " + field.getNumberOfHotels());
            if (field.houseBuildable()) {
                if (active_player.numberOfBuildingsAffordable(price_to_build) > 0) {       // true if the player can aford at least one apartament
                    System.out.println("Es pot construir fins a " + numberOfHouseBuildable + " apartaments a un preu de " + price_to_build + "€ per apartament");
                    int max_apartaments_buildable = active_player.numberOfBuildingsAffordable(price_to_build);
                    if (max_apartaments_buildable > numberOfHouseBuildable)
                        max_apartaments_buildable = numberOfHouseBuildable;
                    System.out.println("Actualment disposa de " + active_player.getMoney() + "€ que l'hi permet comprar fins a " + max_apartaments_buildable + " apartaments");
                    int quantity = 0;
                    for (boolean end = false; !end; ) {         // Check if it's posible to build quantity apartaments
                        System.out.println("Quina quantitat de apartaments vol edificar? Introdueixi la quantitat: ");
                        quantity = active_player.optionSelection("buildApartment",null,field,null,null,null,max_apartaments_buildable,null);
                        if (quantity <= max_apartaments_buildable)
                            end = true;      // Check if posible to build that many apartaments
                        else
                            System.out.println("Es imposible edificar " + quantity + "apartaments. Introdueix un valor correcte");
                    }
                    int final_price = quantity * price_to_build;        // Calculate final price
                    System.out.println("S'edificaràn " + quantity + " apartaments a un preu total de " + final_price + "€" + " i l'hi quedaràn " + (active_player.getMoney() - final_price) + "€");
                    System.out.println("Accions displonibles:");
                    System.out.println("0. Confirmar");
                    System.out.println("1. Anular");
                    System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                    for (boolean end = false; !end; ) {       // Check if actions value is correct
                        int value = active_player.optionSelection("confirmation",active_player,field,null,null,null,0,null);
                        if (value == 0 || value == 1)
                            end = true;       // Check if actions value is correct
                        if (value == 1) System.out.println("Operacio cancelada");
                        else if (value == 0) {
                            field.build(quantity);          // Build x apartaments (x = quantity)
                            active_player.pay(final_price);         // Modify active player money ( money - final price )
                            System.out.println("Operacio realitzada");
                            output.fileWrite(active_player.getName() + "> Edifica " + quantity + " Apartaments a " + field.getName());
                            // Treure resum final del jugador
                        } else System.out.println("Valor entrat erroni, torni a provar");
                    }
                } else {
                    System.out.println("No te suficients diners per construir cap apartament");
                    output.fileWrite(active_player.getName() + "> No te suficients diners per edificar");
                }
            }else {
                System.out.println("Ja estàn tots els apartaments permesos construits");
                output.fileWrite(active_player.getName() + "> Ja estàn tots els apartaments permesos construits");
            }
    }

    /**
     * @brief Administra l'acció per contruïr un hotel en una propietat.
     * @pre \p field != null
     * @post L'acció per contruïr un hotel dins d'una propietat ha estat gestionada.
     * @param field terreny on s'ha de contruïr apartaments.
     */
    private void buildHotel(BoxField field) {
        if (field.hotelBuildable()) {
            int price_to_build = field.priceToBuild();
            System.out.println("Es pot construir un hotel a un preu de " + price_to_build + "€");
            if (active_player.getMoney() >= price_to_build) {
                System.out.println("Actualment disposa de " + active_player.getMoney() + "€");
                System.out.println("S'edificarà un hotel a un preu total de " + price_to_build + "€" + " i l'hi quedaràn " + (active_player.getMoney() - price_to_build) + "€");
                System.out.println("Accions displonibles:");
                System.out.println("0. Confirmar");
                System.out.println("1. Anular");
                System.out.println("Indiqui amb el numero corresponent la accio que vol realitzar: ");
                for (boolean end = false; !end; ) {       // Check if actions value is correct
                    int value = active_player.optionSelection("confirmation",active_player,field,null,null,null,0,null);
                    if (value == 0 || value == 1)
                        end = true;       // Check if actions value is correct
                    if (value == 1) System.out.println("Operacio cancelada");
                    else if (value == 0) {
                        field.build(1);          // Build the hotel (quantity = 1)
                        active_player.pay(price_to_build);         // Modify active player money ( money - hotel price )
                        System.out.println("Operacio realitzada");
                        output.fileWrite(active_player.getName() + "> Edifica 1 Hotel a " + field.getName());
                        // Treure resum final del jugador
                    } else System.out.println("Valor entrat erroni, torni a provar");
                }
            } else {
                System.out.println("No te suficients diners per construir el hotel");
                output.fileWrite(active_player.getName() + "> No te suficients diners per edificar");
            }
        }
        else {
            System.out.println("Encara no es pot construir un hotel");
            output.fileWrite(active_player.getName() + "> Encara no es pot construir un hotel");
        }
    }

    /**
     * @brief Retorna les targetes sort del joc del Monopoly.
     * @pre \p true.
     * @post Les targetes sort han estat retornades.
     * @return ArrayList de les targetes sort de Monopoly.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * @brief Retorna la classe de sortida de dades
     * @pre \p true.
     * @post el objecte ha sigut retornat
     * @return output manager
     */
    public OutputManager getOutput() {
        return output;
    }
}
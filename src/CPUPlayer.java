import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CPUPlayer extends Player{

    private final Random rand = new Random();

    /**
     * @brief Constructor de Player de tipus CPU
     * @param name             nom del Jugador.
     * @param initial_money    quantitat de diners inicials dels que disposa un Jugador.
     * @param initial_position posició inicial del Jugador.
     * @pre true
     * @post Crea un jugador de tipus CPU amb els atributs entrats.
     */
    public CPUPlayer(String name, int initial_money, int initial_position) {
        super(name, initial_money, initial_position,"CPU");
    }

    /**
     * @brief Pregunta a la CPU segons el tipus entrat
     * @param type              tipus de pregunta a la CPU
     * @param player            posible jugador necesitat per la funcio
     * @param field             posible terreny necesitat per la funcio
     * @param options           llista de opcions posibles a triar
     * @param players           llista de jugadors de la partida
     * @param card              posible carta necesitada per la funcio
     * @param value             posible valor enter necesitat per la funcio
     * @pre \p type equival a una pregunta a la CPU (optionalActionSelector,start,buy,confirmation,buildChoice,build,buildApartment,betQuantity,betQuantity,betValue,cardGetPlayerSelect,cardGetFieldSelect,cardGivePlayerSelect,cardGiveFieldSelect,ardPayPlayerSelect,postposableLuckCardChoice,buyPlayerSelect,buyFieldSelect,buyInitalOffer,sellFieldSelect,sellInitalOffer,sellBuyerOffer,loanPlayerSelect,loanInitialOffer,bankruptcy)
     * @post el valor escollit per la CPU s'ha retornat
     * @return el valor escollit per la CPU
     */
    public int optionSelection(String type, Player player, BoxField field, ArrayList<Integer> options, ArrayList<Player> players, Card card, int value, ArrayList<optionalActions> optional_actions) {
        int return_value = 0;
        switch (type) {
            case "optionalActionSelector":
                return_value = optionalActionSelector(player,optional_actions);
                break;
            case "start":
                return_value = start(value);
                break;
            case "buy":
                return_value = buy(field);
                break;
            case "confirmation":
                return_value = 0;
                break;
            case "buildChoice":
                return_value = buildChoice(field);
                break;
            case "build":
                return_value = build(field);
                break;
            case "buildApartment":
                return_value = buildApartment(field,value);
                break;
            case "betQuantity":
                return_value = betQuantity();
                break;
            case "betValue":
                return_value = betValue(betQuantity());
                break;
            case "cardGetPlayerSelect":
                return_value = cardGetPlayerSelect(options,players);
                break;
            case "cardGetFieldSelect":
                return_value = cardGetFieldSelect();
                break;
            case "cardGivePlayerSelect":
                return_value = cardGivePlayerSelect(players);
                break;
            case "cardGiveFieldSelect":
                return_value = cardGiveFieldSelect();
                break;
            case "cardPayPlayerSelect":
                return_value = cardPayPlayerSelect(players);
                break;
            case "postposableLuckCardChoice":
                return_value = postposableLuckCardChoice(card);
                break;
            case "buyPlayerSelect":
                buyPlayerSelect(players);
                break;
            case "buyFieldSelect":
                buyFieldSelect();
                break;
            case "buyInitalOffer":
                return_value = buyInitalOffer(field);
                break;
            case "sellFieldSelect":
                return_value = sellFieldSelect();
                break;
            case "sellInitalOffer":
                return_value = sellInitalOffer(field);
                break;
            case "sellBuyerOffer":
                return_value = sellBuyerOffer(field,value);
                break;
            case "loanPlayerSelect":
                return_value = loanPlayerSelect(players);
                break;
            case "loanInitialOffer":
                return_value = loanInitialOffer();
                break;
            case "bankruptcy":
                return_value = bankruptcy();
            default:
        }
        System.out.println("CPU CHOCIE: " + return_value);
        return return_value;
    }

    /**
     * @brief Pregunta a la CPU segons el tipus entrat
     * @param type              tipus de pregunta a la CPU
     * @param player            posible jugador necesitat per la funcio
     * @param field             posible terreny necesitat per la funcio
     * @param value             posible enter  necesitat per la funcio
     * @param second_value             posible enter  necesitat per la funcio
     * @pre \p type equival a una pregunta a la CPU (throwDice,buyBuyerOffer,buySellerOffer,loanInterestOffer,loanTurnsOffer)
     * @post el string escollit per la CPU s'ha retornat
     * @return el string escollit per la CPU
     */
    public String stringValueSelection(String type, Player player, BoxField field, int value, int second_value) {
        String return_value = null;
        switch (type) {
            case "throwDice" :
                return "\n";
            case "buyBuyerOffer":
                return_value = buyBuyerOffer(field,value);
                break;
            case "buySellerOffer":
                return_value = buySellerOffer(field,value);
                break;
            case "loanInterestOffer":
                return_value = loanInterestOffer(player,value,second_value);
                break;
            case "loanTurnsOffer":
                return_value = loanTurnsOffer();
                break;
            default:
                break;
        }
        System.out.println("CPU CHOCIE: " + return_value);
        return return_value;
    }

    /**
     * @brief Seleciona la millor accio opcional per la CPU al final del torn segons les probabilitats de guanyar diners o terrenys
     * @param player Jugador actual
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int optionalActionSelector(Player player,ArrayList<optionalActions> optional_actions) {
        HashMap<Integer,Integer> bestMoves = new HashMap<>();
        bestMoves.put(0,20);

        int pos_iterator = 1;
        for (optionalActions opaction : optional_actions) {
            switch (opaction.getClass().getName()) {
                case "OpActBuy":
                    if (player.getMoney() >= 20000)
                        bestMoves.put(pos_iterator, player.getMoney() / 1000);  // Ex: money = 40000 / 1000 = 40
                    else bestMoves.put(pos_iterator, 0);
                    break;
                case "OpActSell":
                    if (player.getMoney() <= 20000 /*&& player.getFields().size() > 0*/)
                        bestMoves.put(pos_iterator, player.getMoney() / 1000);
                    else bestMoves.put(pos_iterator, 0);
                    break;
                case "OpActLoan":
                    if (player.getMoney() <= 5000)
                        bestMoves.put(pos_iterator, player.getMoney() / 1000 * 20);  // Ex: money = 3000 => 3000/1000 = 3 => 5-3 = 2 => 2*20 = 40%
                    else bestMoves.put(pos_iterator, 0);
                    break;
                case "OpActLuckCard":
                    List<Card> cards = player.getLuckCards();
                    int prob = 0;
                    for (Card aux : cards) {
                        switch (aux.getType()) {
                            case "CHARGE":
                                prob = prob + 40;
                                break;
                            case "GET":
                                prob = prob + 50;
                                break;
                            case "GO":
                                prob = prob + 10;
                                break;
                        }
                    }
                    bestMoves.put(pos_iterator, prob);
                    break;
            }
            pos_iterator++;
        }
        int bestMove = 0;
        for (HashMap.Entry<Integer,Integer> entry : bestMoves.entrySet()) {
            if (entry.getValue() > bestMoves.get(bestMove)) {
                bestMove = entry.getKey();
            }
        }
        return bestMove;
    }

    /**
     * @brief Seleciona la millor accio de recompensa al passar per la casella de sortida
     * @param options quantitat de recompenses posibles -1
     * @pre options es igual a la quantitat de recompenses posibles -1
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int start(int options) {
        return rand.nextInt(options) + 1;
    }

    /**
     * @brief Seleciona si compra o no el terreny entrat
     * @param field Terreny a comprar
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buy(BoxField field) {
        if (this.getMoney()-field.getPrice() >= 2000) return 1;
        else return 0;
    }

    /**
     * @brief Seleciona si construeix o no en el terreny
     * @param field Terreny a contruir
     * @pre field buildable == true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buildChoice(BoxField field) {
        if (this.getMoney() > field.priceToBuild()) return 1;
        else return 0;
    }

    /**
     * @brief Seleciona si construeix un apartament o un hotel
     * @param field Terreny a contruir
     * @pre player money() > field priceToBuild ( es pot parmetre minim un apartament )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int build(BoxField field) {
        if (field.hotelBuildable()) if (buildApartment(field,10) > 0) return 2;
        else if (field.houseBuildable()) if (this.getMoney()-field.priceToBuild() > 2000) return 1;
        return 0;
    }

    /**
     * @brief Seleciona quants apartament vol construir
     * @param field Terreny a contruir
     * @param max numero maxim de apartaments permesos i asequibles per el jugador
     * @pre player money() > field priceToBuild ( es pot parmetre minim un apartament )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buildApartment(BoxField field, int max) {
        int max_to_buid = (this.getMoney() / 100) * 30 / field.priceToBuild();
        return Math.min(max_to_buid, max);
    }

    /**
     * @brief Seleciona la millor quanitat a apostar ( el 10% del seu capital )
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int betQuantity() {
        return (this.getMoney()/10) ; //10% de aposta
    }

    /**
     * @brief Seleciona la millor aposta a fer depenguent de la quanitat apostada
     * @param betQuantity quantiat apostada
     * @pre betQuantity < player money
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int betValue(int betQuantity) {
        if (betQuantity < 5000) return rand.nextInt(11-8) +8; // 8->11
        else if (betQuantity < 10000) return rand.nextInt(9-5) +5; // 5->9
        return rand.nextInt(7-3) +3; // 3->7*/
    }

    /**
     * @brief Seleciona el jugador amb mes cartes
     * @param options numero de opcions disponibles
     * @param players llista de jugadors de la partida
     * @pre options size > 0
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGetPlayerSelect (ArrayList<Integer> options,ArrayList<Player> players) {
        int min_fields = -1;
        int player_chosen = -1;
        for (int i=0;i<players.size();i++) {
            if (!options.contains(i) && players.get(i).getFields().size() > min_fields) {
                min_fields = players.get(i).getFields().size();
                player_chosen = i;
            }
        }
        if (player_chosen == -1) {
            System.out.println("HI HA HAGUT UN ERROR");  // Introduir exception
        }
        return player_chosen;
    }

    /**
     * @brief Selecciona el terrent amb major valor
     * @pre field_owner fields size > 0 ( El jugador te terrenys disponibles )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGetFieldSelect () {
        int result = 0;
        int max_value = this.getFields().get(0).getPrice();
        for (BoxField aux : this.getFields()) {
            if (aux.getPrice() > max_value) result = this.getFields().indexOf(aux);
        }
        return result;
    }

    /**
     * @brief Seleciona el jugador amb mes cartes
     * @param players llista de jugadors de la partida
     * @pre options size > 0
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGivePlayerSelect (ArrayList<Player> players) {
        int min_fields = -1;
        int player_chosen = -1;
        for (Player player : players) {
            if (player != this && player.getFields().size() > min_fields) {
                min_fields = player.getFields().size();
                player_chosen = players.indexOf(player);
            }
        }
        return player_chosen;
    }

    /**
     * @brief Selecciona el terrent amb major valor
     * @pre field_owner fields size > 0 ( El jugador te terrenys disponibles )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGiveFieldSelect () {
        int result = 0;
        int value = this.getFields().get(0).getPrice();
        for (BoxField aux : this.getFields()) {
            if (aux.getPrice() < value) {
                result = this.getFields().indexOf(aux);
                value = aux.getPrice();
            }
        }
        return result;
    }

    /**
     * @brief Selecciona el jugador a qui pagar
     * @param players llista de jugadors
     * @pre players size > 1
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardPayPlayerSelect (ArrayList<Player> players) {
        int result = 0;
        int min_value;
        if (players.get(0) != this) min_value = players.get(0).getMoney();
        else {
            min_value = players.get(1).getMoney();
            result = 1;
        }
        for (Player aux : players) {
            if (aux != this && aux.getMoney() < min_value) {
                result = players.indexOf(aux);
                min_value = aux.getMoney();
            }
        }
        return result;
    }

    /**
     * @brief Selecciona si vol executar una carta de sort o guardar-sela
     * @param card carta de sort
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int postposableLuckCardChoice(Card card) {
        if (card.getType().equals("CHARGE") || card.getType().equals("GET") || card.getType().equals("GO")) return 1;
        else return 0;
    }

    /**
     * @brief Seleccio del jugador a la funcio Buy
     * @param players llista de jugadors
     * @pre players size > 0
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buyPlayerSelect(ArrayList<Player> players) {
        int max_properties = -1;
        int chosen_index = -1;
        for (Player aux : players) {
            if (aux != this && aux.getFields().size() > max_properties) {
                max_properties = aux.getFields().size();
                chosen_index = players.indexOf(aux);
            }
        }
        return chosen_index;
    }

    /**
     * @brief Seleccio del terreny a la funcio Buy
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buyFieldSelect() {
        int min_value = this.getFields().get(0).getPrice();
        int chosen = 0;
        for (BoxField aux : this.getFields()) {
            if (aux.getPrice() < min_value) {
                min_value = aux.getPrice();
                chosen = this.getFields().indexOf(aux);
            }
        }
        return chosen;
    }

    /**
     * @brief Seleccio  de la oferta inicial a la funcio Buy
     * @param field terreny a comprar
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buyInitalOffer(BoxField field) {
        if (field.getPrice() > this.getMoney()) return this.getMoney();
        else {
            if (this.getMoney() > field.getPrice() * 1.5) return (int) (rand.nextInt((int) (field.getPrice() * 1.5 - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
            else return (int) (rand.nextInt((int) (field.getPrice() - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
        }

    }

    /**
     * @brief Seleccio de la oferta del comprador a la funcio Buy en negociació
     * @param field terreny a comprar
     * @param actual_offer ultima oferta activa
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private String buyBuyerOffer(BoxField field, int actual_offer) {
        if (actual_offer > field.getPrice() * 1.5) return "no";
        else if (actual_offer < field.getPrice() * 1.2) return "ok";
        int max = actual_offer;
        int min = (int) (field.getPrice() * 0.8);
        int result = rand.nextInt(max-min) + min;
        return Integer.toString(result);
    }

    /**
     * @brief Seleccio de la oferta del venedor a la funcio Buy en negociació
     * @param field terreny a comprar
     * @param actual_offer ultima oferta activa
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private String buySellerOffer(BoxField field, int actual_offer) {
        if (actual_offer < field.getPrice()) return "no";
        else if (actual_offer > field.getPrice() * 1.3) return "ok";
        int max = (int) (field.getPrice() * 1.7);
        int min = actual_offer;
        int result = rand.nextInt(max-min) + min;
        return Integer.toString(result);
    }

    /**
     * @brief Seleccio del terreny a la funcio Sell
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int sellFieldSelect() {
        int min_value = this.getFields().get(0).getPrice();
        int chosen = 0;
        for (BoxField aux : this.getFields()) {
            if (aux.getPrice() < min_value) {
                min_value = aux.getPrice();
                chosen = this.getFields().indexOf(aux);
            }
        }
        return chosen;
    }

    /**
     * @brief Seleccio  de la oferta inicial a la funcio Sell
     * @param field terreny a vendre
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int sellInitalOffer(BoxField field) {
        if (field.getPrice() > this.getMoney()) return this.getMoney();
        else {
            if (this.getMoney() > field.getPrice() * 1.5) return (int) (rand.nextInt((int) (field.getPrice() * 1.5 - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
            else return (int) (rand.nextInt((int) (field.getPrice() - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
        }
    }

    /**
     * @brief Seleccio de la oferta del comprador a la funcio Sell
     * @param field terreny a comprar
     * @param actual_offer ultima oferta activa
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int sellBuyerOffer(BoxField field, int actual_offer) {
        if (actual_offer > field.getPrice() * 1.5 || actual_offer > this.getMoney()) return -1;
        int max = (int) (actual_offer * 1.2);
        int min = actual_offer;
        if (max-min <= 0 || min <= 0 || max <= 0) return -1;
        return rand.nextInt(max-min) + min;
    }

    /**
     * @brief Seleccio del jugador a la funcio Loan
     * @param players llista de jugadors
     * @pre players size > 0
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int loanPlayerSelect(ArrayList<Player> players) {
        int max_money = -1;
        int chosen_index = -1;
        for (Player aux : players) {
            if (aux != this && aux.getMoney() > max_money) {
                max_money = aux.getMoney();
                chosen_index = players.indexOf(aux);
            }
        }
        return chosen_index;
    }

    /**
     * @brief Seleccio  de la oferta inicial adel prestec
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int loanInitialOffer() {
        int min = (int) (this.getMoney() * 0.1);
        int max = (int) (this.getMoney() * 0.5);
        if (max-min <= 0) return 0;
        else return rand.nextInt(max-min) + min;
    }

    /**
     * @brief Seleccio de la acceptacio, denegació o valor del interes del prestec
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return un enter amb el interes del prestec, no si rebutja la oferta o ok si la accepta
     */
    private String loanInterestOffer(Player player,int offer,int interests) {
        if (interests == -1) {
            return String.valueOf(rand.nextInt(30 - 5) + 5);
        }
        else if (player != this) {
            if (offer > player.getMoney() * 0.3) return "no";
            else return String.valueOf(rand.nextInt(30 - 5) + 5);
        }
        else {
            if (offer >= player.getMoney() * 0.2) return "ok";
            else return "no";
        }
    }

    /**
     * @brief Seleccio de dels torns que durarà del prestec
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return numero de torns que durarà el prestec
     */
    private String loanTurnsOffer() {
        int turns = rand.nextInt(6-1) + 1;
        return String.valueOf(turns);
    }

    /**
     * @brief Seleccio de la opcio a triar si s'entra en bancarrota ( la CPU mai farà cap altre opcio que declarar fallida perque sino la partida pot durar eternament )
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return 0
     */
    private int bankruptcy() {
        return 0;
    }
}

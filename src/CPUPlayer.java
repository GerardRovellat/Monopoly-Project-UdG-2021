import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CPUPlayer extends Player{

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
    public int optionSelection(String type,Player player, Field field, ArrayList<Integer> options, ArrayList<Player> players, Card card, int value) {
        int return_value = 0;
        switch (type) {
            case "optionalActionSelector":
                return_value = optionalActionSelector(player);
                break;
            case "start":
                return_value = start(player);
                break;
            case "buy":
                return_value = buy(player,field);
                break;
            case "confirmation":
                return_value = 0;
                break;
            case "buildChoice":
                return_value = buildChoice(player,field);
                break;
            case "build":
                return_value = build(player,field);
                break;
            case "buildApartment":
                return_value = buildApartment(player,field,value);
                break;
            case "betQuantity":
                return_value = betQuantity(player);
                break;
            case "betValue":
                return_value = betValue(betQuantity(player));
                break;
            case "cardGetPlayerSelect":
                return_value = cardGetPlayerSelect(options,players);
                break;
            case "cardGetFieldSelect":
                return_value = cardGetFieldSelect(player);
                break;
            case "cardGivePlayerSelect":
                return_value = cardGivePlayerSelect(options,players);
                break;
            case "cardGiveFieldSelect":
                return_value = cardGiveFieldSelect(player);
                break;
            case "cardPayPlayerSelect":
                return_value = cardPayPlayerSelect(player,players);
                break;
            case "postposableLuckCardChoice":
                return_value = postposableLuckCardChoice(card);
                break;
            case "buyPlayerSelect":
                buyPlayerSelect(player,players);
                break;
            case "buyFieldSelect":
                buyFieldSelect(player);
                break;
            case "buyInitalOffer":
                return_value = buyInitalOffer(player,field);
                break;
            case "sellFieldSelect":
                return_value = sellFieldSelect(player);
                break;
            case "sellInitalOffer":
                return_value = sellInitalOffer(player,field);
                break;
            case "sellBuyerOffer":
                return_value = sellBuyerOffer(player,field,value);
                break;
            case "loanPlayerSelect":
                return_value = loanPlayerSelect(player,players);
                break;
            case "loanInitialOffer":
                return_value = loanInitialOffer(player);
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
    public String stringValueSelection(String type, Player player, Field field, int value, int second_value) {
        String return_value = null;
        switch (type) {
            case "throwDice" :
                return "\n";
            case "buyBuyerOffer":
                return_value = buyBuyerOffer(player,field,value);
                break;
            case "buySellerOffer":
                return_value = buySellerOffer(player,field,value);
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
    private int optionalActionSelector(Player player) {
        HashMap<Integer,Integer> bestMoves = new HashMap<>();
        bestMoves.put(0,20);

        if (player.getMoney()<=20000) bestMoves.put(1,20-(player.getMoney()/1000)*5);  // Ex: money = 5000 => 5000/1000 = 5 => 20-5 = 15 => 15*5 = 75%
        else bestMoves.put(1,0);

        if (player.getMoney()>=20000) bestMoves.put(2,player.getMoney()/1000);
        else bestMoves.put(2,0);

        if (player.getMoney()<=5000) bestMoves.put(3,player.getMoney()/1000*20);  // Ex: money = 3000 => 3000/1000 = 3 => 5-3 = 2 => 2*20 = 40%
        else bestMoves.put(3,0);

        List<Card> cards = player.getLuckCards();
        int prob = 0;
        for (Card aux : cards) {
            if (aux.getType()=="CHARGE") prob = prob + 40;
            else if (aux.getType()=="GET") prob = prob + 50;
            else if (aux.getType()=="GO") prob = prob + 10;
        }
        bestMoves.put(4,prob);
        int bestMove = 0;
        for (int i=0;i<4;i++) {
            if (bestMoves.get(i).byteValue() > bestMoves.get(bestMove).longValue()) bestMove = i;
        }
        return bestMove;
    }

    /**
     * @brief Seleciona la millor accio de recompensa al passar per la casella de sortida
     * @param player Jugador actual
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    // ERROR: Si hi ha nomes una recompensa ---------------------------------------------------------------------------------------------------!!!!!!!!!!!!!!!!!!!!!!
    private int start(Player player) {
        if (player.getMoney() < 15000) return 1;
        else return 2;
    }

    /**
     * @brief Seleciona si compra o no el terreny entrat
     * @param player Jugador actual
     * @param field Terreny a comprar
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buy(Player player,Field field) {
        if (player.getMoney()-field.getPrice() >= 2000) return 1;
        else return 0;
    }

    /**
     * @brief Seleciona si construeix o no en el terreny
     * @param player Jugador actual
     * @param field Terreny a contruir
     * @pre field buildable == true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buildChoice(Player player, Field field) {
        if (player.getMoney() > field.priceToBuild()) return 1;
        else return 0;
    }

    /**
     * @brief Seleciona si construeix un apartament o un hotel
     * @param player Jugador actual
     * @param field Terreny a contruir
     * @pre player money() > field priceToBuild ( es pot parmetre minim un apartament )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int build(Player player, Field field) {
        if (field.hotelBuildable()) {
            //if (player.getMoney()-field.priceToBuild() > 2000) return 2;
            if (buildApartment(player,field,10) > 0) return 2;
        }
        else if (field.houseBuildable()) {
            if (player.getMoney()-field.priceToBuild() > 2000) return 1;
        }
        return 0;
    }

    /**
     * @brief Seleciona quants apartament vol construir
     * @param player Jugador actual
     * @param field Terreny a contruir
     * @param max numero maxim de apartaments permesos i asequibles per el jugador
     * @pre player money() > field priceToBuild ( es pot parmetre minim un apartament )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buildApartment(Player player, Field field, int max) {
        int max_to_buid = (player.getMoney() / 100) * 30 / field.priceToBuild();
        if (max_to_buid >= max) return max;
        else return max_to_buid;
    }

    /**
     * @brief Seleciona la millor quanitat a apostar ( el 10% del seu capital )
     * @param player Jugador actual
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int betQuantity(Player player) {
        return (player.getMoney()/10) ; //10% de aposta
    }

    /**
     * @brief Seleciona la millor aposta a fer depenguent de la quanitat apostada
     * @param betQuantity quantiat apostada
     * @pre betQuantity < player money
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int betValue(int betQuantity) {
        Random rand = new Random();
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
     * @param fields_owner propietari dels terreny
     * @pre field_owner fields size > 0 ( El jugador te terrenys disponibles )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGetFieldSelect (Player fields_owner) {
        ArrayList<Field> fields = fields_owner.getFields();
        int iterator = 0;
        int result = 0;
        int value = fields.get(0).getPrice();
        for (Field aux : fields) {
            if (aux.getPrice() > value) result = iterator;
            iterator++;
        }
        return result;
    }

    /**
     * @brief Seleciona el jugador amb mes cartes
     * @param options numero de opcions disponibles
     * @param players llista de jugadors de la partida
     * @pre options size > 0
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGivePlayerSelect (ArrayList<Integer> options,ArrayList<Player> players) {
        int min_fields = -1;
        int player_chosen = -1;
        for (int i=0;i<players.size();i++) {
            if (!options.contains(i) && players.get(i).getFields().size() > min_fields) {
                min_fields = players.get(i).getFields().size();
                player_chosen = i;
            }
        }
        return player_chosen;
    }

    /**
     * @brief Selecciona el terrent amb major valor
     * @param fields_owner propietari dels terreny
     * @pre field_owner fields size > 0 ( El jugador te terrenys disponibles )
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardGiveFieldSelect (Player fields_owner) {
        ArrayList<Field> fields = fields_owner.getFields();
        int iterator = 0;
        int result = 0;
        int value = fields.get(0).getPrice();
        for (Field aux : fields) {
            if (aux.getPrice() < value) {
                result = iterator;
                value = aux.getPrice();
            }
            iterator++;
        }
        return result;
    }

    /**
     * @brief Selecciona el jugador a qui pagar
     * @param current_player jugador actual
     * @param players llista de jugadors
     * @pre players size > 1
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int cardPayPlayerSelect (Player current_player,ArrayList<Player> players) {
        int result = 0;
        int min_value = -1;
        if (players.get(0) != current_player) min_value = players.get(0).getMoney();
        else {
            min_value = players.get(1).getMoney();
            result = 1;
        }
        for (Player aux : players) {
            if (aux != current_player && aux.getMoney() < min_value) {
                result = players.indexOf(aux);
                min_value = aux.getMoney();
            }
        }
        if (result == -1 || min_value == -1) ;//Throw error
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
        if (card.getType() == "CHARGE" || card.getType() == "GET" || card.getType() == "GO") return 1;
        else return 0;
    }

    /**
     * @brief Seleccio del jugador a la funcio Buy
     * @param player jugador actual
     * @param players llista de jugadors
     * @pre players size > 0
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    private int buyPlayerSelect(Player player, ArrayList<Player> players) {
        int max_properties = -1;
        int chosen_index = -1;
        for (Player aux : players) {
            if (aux != player && aux.getFields().size() > max_properties) {
                max_properties = aux.getFields().size();
                chosen_index = players.indexOf(aux);
            }
        }
        return chosen_index;
    }

    /**
     * @brief Seleccio del terreny a la funcio Buy
     * @param player jugador actual
     * @pre true
     * @post la opcio escollida s'ha retornat
     * @return la opcio escollida
     */
    //----------------------------------------------------------------------------------------COMENTAT FINS AQUI--------------------------------------------------------
    private int buyFieldSelect(Player player) {
        int min_value = player.getFields().get(0).getPrice();
        int chosen = 0;
        for (Field aux : player.getFields()) {
            if (aux.getPrice() < min_value) {
                min_value = aux.getPrice();
                chosen = player.getFields().indexOf(aux);
            }
        }
        return chosen;
    }

    private int buyInitalOffer(Player player, Field field) {
        Random rand = new Random();
        if (field.getPrice() > player.getMoney()) return player.getMoney();
        else {
            if (player.getMoney() > field.getPrice() * 1.5) return (int) (rand.nextInt((int) (field.getPrice() * 1.5 - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
            else return (int) (rand.nextInt((int) (field.getPrice() - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
        }

    }

    private String buyBuyerOffer(Player player, Field field, int actual_offer) {
        if (actual_offer > field.getPrice() * 1.5) return "no";
        else if (actual_offer < field.getPrice() * 1.2) return "ok";
        Random rand = new Random();
        int max = actual_offer;
        int min = (int) (field.getPrice() * 0.8);
        int result = rand.nextInt(max-min) + min;
        return Integer.toString(result);
    }

    private String buySellerOffer(Player player, Field field, int actual_offer) {
        if (actual_offer < field.getPrice()) return "no";
        else if (actual_offer > field.getPrice() * 1.3) return "ok";
        Random rand = new Random();
        int max = (int) (field.getPrice() * 1.7);
        int min = (int) actual_offer;
        int result = rand.nextInt(max-min) + min;
        return Integer.toString(result);
    }

    private int sellFieldSelect(Player player) {
        int min_value = player.getFields().get(0).getPrice();
        int chosen = 0;
        for (Field aux : player.getFields()) {
            if (aux.getPrice() < min_value) {
                min_value = aux.getPrice();
                chosen = player.getFields().indexOf(aux);
            }
        }
        return chosen;
    }

    private int sellInitalOffer(Player player, Field field) {
        Random rand = new Random();
        if (field.getPrice() > player.getMoney()) return player.getMoney();
        else {
            if (player.getMoney() > field.getPrice() * 1.5) return (int) (rand.nextInt((int) (field.getPrice() * 1.5 - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
            else return (int) (rand.nextInt((int) (field.getPrice() - field.getPrice() * 0.5)) + field.getPrice() * 0.5);
        }
    }

    private int sellBuyerOffer(Player player, Field field, int actual_offer) {
        if (actual_offer > field.getPrice() * 1.5 || actual_offer > player.getMoney()) return -1;
        Random rand = new Random();
        int max = (int) (actual_offer * 1.2);
        int min = actual_offer;
        if (max-min <= 0 || min <= 0 || max <= 0) {
            return -1;
        }
        int result = rand.nextInt(max-min) + min;
        return result;
    }

    private int loanPlayerSelect(Player player, ArrayList<Player> players) {
        int max_money = -1;
        int chosen_index = -1;
        for (Player aux : players) {
            if (aux != player && aux.getMoney() > max_money) {
                max_money = aux.getMoney();
                chosen_index = players.indexOf(aux);
            }
        }
        return chosen_index;
    }

    private int loanInitialOffer(Player loan_player) {
        Random rand = new Random();
        int min = (int) (loan_player.getMoney() * 0.1);
        int max = (int) (loan_player.getMoney() * 0.5);
        if (max-min <= 0) return 0;
        else return rand.nextInt(max-min) + min;
    }

    private String loanInterestOffer(Player player,int offer,int interests) {
        Random rand = new Random();
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

    private String loanTurnsOffer() {
        Random rand = new Random();
        int turns = rand.nextInt(6-1) + 1;
        return String.valueOf(turns);
    }

    private int bankruptcy() {
        return 0;
    }
}

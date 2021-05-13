import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

/**
 * @author Gerard Rovellat
 * @class JSONManager
 * @brief JSONManager administra totes les funcions relacionades amb llegir fitxers JSON i també la creació del fitxer
 * de desenvolupament de la partida.
 */
public class JSONManager {

    private Board board = new Board();                                  ///< Tauler llegit del fitxer JSON pel Monopoly.
    private ArrayList<optionalActions> actions = new ArrayList<>();     ///< Llistat d'accions opcionals possibles.
    private ArrayList<String> star_rewards = new ArrayList<>();         ///< Llistat de recompenses que pot donar Start
    private ArrayList<Card> cards = new ArrayList();                    ///< Llistat de targetes sort de Monopoly
    private int initial_money = 0;                                      ///< Diners inicials de cada Jugador.
    private String rules_file;                                          ///< Fitxer definicio regles del joc de Monopoly
    private String board_file;                                          ///< Fitxer definicio tauler del joc de Monopoly

    /**
     * @brief Constructor de JSONManager
     * @pre \p true
     * @post Crea la classe JSONManager amb el nom dels fitxers d'entrada JSON.
     * @param rules nom del fitxer JSON el qual descriu les regles del joc.
     * @param board nom del fitxer JSON el qual descriu el tauler i les seves propietats.
     */
    public JSONManager(String rules, String board) {
        rules_file = rules;
        board_file = board;
    }

    /**
     * @brief Llegeix els dos fitxers d'entrada \p rules_file i \p board_file per poder crear el joc del Monopoly.
     * @pre \p true
     * @post Retorna el joc del Monopoly amb les configuracions de \p rules_file i \p board_file.
     * @return Monopoly \p monopoly amb totes les característiques establertes.
     */
    public Monopoly readFile() {
        this.board = readBoard();
        this.actions = readRules();
        Monopoly monopoly = new Monopoly(board,actions,cards,initial_money,star_rewards);
        return monopoly;
    }

    /**
     * @brief Escriu el fitxer de desenvolupament de la partida de Monopoly.
     * @pre \p true
     * @post El fitxer de desenvolupament de la partida ha estat escrit.
     */
    public void writeFile() {

    }

    /**
     * @brief Llegeix \p rules_file fitxer JSON i configura les propietats llegides del joc del Monopoly.
     * @pre \p true
     * @post \p rules_file ha estat llegit.
     * @return ArrayList amb totes les accions opcionals possibles que el jugador pot fer en aquest joc.
     */
    private ArrayList<optionalActions> readRules() {
        //Start Gson variables.
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        FileReader reader;
        ArrayList possible_actions = new ArrayList<optionalActions>();
        try {
            //Parse file to JsonElement data and get as JsonObject.
            reader = new FileReader(rules_file);
            JsonElement data = parser.parse(reader);
            JsonObject j_object = data.getAsJsonObject();

            //First element "modalitat"
            String mode = j_object.get("modalitat").getAsString();
            /**FALTA TRACTAR EL ERROR**/

            //Second element array "accionsNoAplicables"
            JsonArray restricted_actions = j_object.get("accionsNoAplicables").getAsJsonArray();
            List restricted_action_list = new ArrayList<String>();
            for (JsonElement array_element : restricted_actions){
                restricted_action_list.add(array_element.toString());
            }
            if(!restricted_action_list.contains("VENDRE")){
                Sell sell_action  = new Sell();
                possible_actions.add(sell_action);
            }
            if(!restricted_action_list.contains("COMPRAR")){
                Buy buy_action = new Buy();
                possible_actions.add(buy_action);
            }
            if(!restricted_action_list.contains("PRESTEC")){
                Loan loan_action = new Loan();
                possible_actions.add(loan_action);
            }
            if(!restricted_action_list.contains("SORT")){
                LuckCard luck_card_action = new LuckCard();
                possible_actions.add(luck_card_action);
            }


            //Third element "dinersInicials"
            int initial_money = j_object.get("dinersInicials").getAsInt();
            this.initial_money = initial_money;

            //Fourth element array "recompensesCasellaSortida"
            JsonArray start_box_rewards = j_object.get("recompensesCasellaSortida").getAsJsonArray();
            ArrayList start_box_rewards_list = new ArrayList<String>();
            for (JsonElement array_element : start_box_rewards) {
                start_box_rewards_list.add(array_element.toString().replace("\"",""));
            }
            this.star_rewards=start_box_rewards_list;

            //Fifth element "percentatgePreuEdificisHipoteca"
            int percentage_mortgage_buildings = j_object.get("percentatgePreuEdificisHipoteca").getAsInt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return possible_actions;
    }

    /**
     * @brief Llegeix \p bord_file fitxer JSON i configura les propietats del tauler llegides al joc del Monopoly.
     * @pre true
     * @post \p board_file ha estat llegit.
     * @return Tauler amb totes les seves propietats.
     */
    private Board readBoard() {
        //Start Gson variables.
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        FileReader reader;
        Board board = new Board();
        try {
            //Parse file to JsonElement data and get as JsonObject.
            reader = new FileReader(board_file);
            JsonElement data = parser.parse(reader);
            JsonObject j_object = data.getAsJsonObject();

            int boxes_nr = j_object.get("nombreCaselles").getAsInt();
            board.setBoxesNr(boxes_nr);

            Start start = new Start(1,"START");
            board.addBox(start);

            JsonArray field_boxes = j_object.get("casellesTerreny").getAsJsonArray();
            for(JsonElement array_element : field_boxes){

                int box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                String box_name = array_element.getAsJsonObject().get("nom").getAsString();
                int box_price = array_element.getAsJsonObject().get("preu").getAsInt();
                int box_mortgage = array_element.getAsJsonObject().get("preuHipoteca").getAsInt();
                String box_group = "";
                if(array_element.getAsJsonObject().get("agrupacio").isJsonNull()){
                    box_group = "null";
                }
                else box_group = array_element.getAsJsonObject().get("agrupacio").getAsString();
                int box_basic_rent = array_element.getAsJsonObject().get("preuLloguerBasic").getAsInt();
                int box_group_rent = 0;
                if(array_element.getAsJsonObject().get("preuLloguerAgrupacio").isJsonNull()){
                    box_group_rent = -1;
                }
                else box_group_rent = array_element.getAsJsonObject().get("preuLloguerAgrupacio").getAsInt();
                String box_buildable = array_element.getAsJsonObject().get("construible").getAsString();
                int box_max_houses = array_element.getAsJsonObject().get("maxCases").getAsInt();
                int box_houses_price = 0;
                if(array_element.getAsJsonObject().get("preuCasa").isJsonNull()) {
                    box_houses_price = -1;
                }
                else box_houses_price = array_element.getAsJsonObject().get("preuCasa").getAsInt();
                boolean box_hotel = array_element.getAsJsonObject().get("hotel").getAsBoolean();
                int box_hotel_price = 0;
                if(array_element.getAsJsonObject().get("preuHotel").isJsonNull()){
                    box_hotel_price = -1;
                }
                else box_hotel_price = array_element.getAsJsonObject().get("preuHotel").getAsInt();
                JsonArray houses_rents = array_element.getAsJsonObject().get("lloguerAmbCases").getAsJsonArray();
                ArrayList houses_rents_list = new ArrayList<Integer>();
                for(JsonElement rents : houses_rents){
                    houses_rents_list.add(rents.getAsInt());
                }
                int hotel_rent = 0;
                if(array_element.getAsJsonObject().get("lloguerAmbHotel").isJsonNull()){
                    hotel_rent = -1;
                }
                else hotel_rent = array_element.getAsJsonObject().get("lloguerAmbHotel").getAsInt();

                Field field = new Field(box_nr,"FIELD",box_name,box_price,box_group,box_basic_rent,box_group_rent,
                        box_buildable,box_max_houses,box_houses_price,box_hotel,box_hotel_price,houses_rents_list,hotel_rent);
                board.addBox(field);

            }
            JsonArray jail_boxes = j_object.get("casellesPreso").getAsJsonArray();
            for(JsonElement array_element : jail_boxes){
                int box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                String box_name = array_element.getAsJsonObject().get("nom").getAsString();

                Empty empty = new Empty(box_nr,"EMPTY",box_name);
                board.addBox(empty);
            }

            JsonArray direct_command_boxes = j_object.get("casellesComandaDirecta").getAsJsonArray();
            for(JsonElement array_element : direct_command_boxes) {
                int box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                String box_action = array_element.getAsJsonObject().get("accio").getAsString();
                switch (box_action) {
                    case "PAGAR":
                        int pay_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardPay pay_card = new CardPay( false, pay_amount);
                        directCommand pay_direct_command = new directCommand(box_nr,"DIRECTCOMMAND",pay_card);
                        board.addBox(pay_direct_command);
                        break;
                    case "MULTA":
                        int fine_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardFine fine_card = new CardFine( false, fine_amount);
                        directCommand fine_direct_command = new directCommand(box_nr,"DIRECTCOMMAND",fine_card);
                        board.addBox(fine_direct_command);
                        break;
                    case "COBRAR":
                        int charge_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardCharge charge_card = new CardCharge(false, charge_amount);
                        directCommand charge_direct_command = new directCommand(box_nr,"DIRECTCOMMAND",charge_card);
                        board.addBox(charge_direct_command);
                        break;
                    case "ANAR":
                        int go_box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                        CardGo go_card = new CardGo(false, go_box_nr);
                        directCommand go_direct_command = new directCommand(box_nr,"DIRECTCOMMAND",go_card);
                        board.addBox(go_direct_command);
                        break;
                    case "DONAR":
                        CardGive give_card = new CardGive(false);
                        directCommand give_direct_command = new directCommand(box_nr,"DIRECTCOMMAND",give_card);
                        board.addBox(give_direct_command);
                        break;
                    case "REBRE":
                        CardGet get_card = new CardGet(false);
                        directCommand get_direct_command = new directCommand(box_nr,"DIRECTCOMMAND",get_card);
                        board.addBox(get_direct_command);
                        break;
                    case "HIPOTECAR":
                        Empty mortatge_direct_command = new Empty(box_nr,"EMPTY",null);
                        board.addBox(mortatge_direct_command);
                    case "DISPENSAR":
                        Empty dispense_direct_command = new Empty(box_nr,"EMPTY",null);
                        board.addBox(dispense_direct_command);
                    case "PRESO":
                        Empty jail_direct_command = new Empty(box_nr,"EMPTY",null);
                        board.addBox(jail_direct_command);
                    case "ANAR_PRESO":
                        Empty go_jail_direct_command = new Empty(box_nr,"EMPTY",null);
                        board.addBox(go_jail_direct_command);
                }
            }

            JsonArray bet_boxes = j_object.get("casellesAposta").getAsJsonArray();
            for(JsonElement array_element : bet_boxes){
                Bet bet = new Bet(array_element.getAsInt(),"BET");
                board.addBox(bet);
            }

            JsonArray luck_cards_boxes = j_object.get("casellesSort").getAsJsonArray();
            for(JsonElement array_element : luck_cards_boxes){
                Box box = new Box(array_element.getAsInt(),"LUCK","LUCK");
                board.addBox(box);
            }

            JsonArray luck_cards = j_object.get("targetesSort").getAsJsonArray();
            ArrayList cards_stack = new ArrayList<Card>();
            for(JsonElement array_element : luck_cards){
                String card_action = array_element.getAsJsonObject().get("accio").getAsString();
                boolean postponable = array_element.getAsJsonObject().get("posposable").getAsBoolean();
                switch (card_action){
                    case "PAGAR":
                        int pay_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardPay pay_card = new CardPay(postponable,pay_amount);
                        cards_stack.add(pay_card);
                        break;
                    case "MULTA":
                        int fine_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardFine fine_card = new CardFine(postponable,fine_amount);
                        cards_stack.add(fine_card);
                        break;
                    case "COBRAR":
                        int charge_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardCharge charge_card = new CardCharge(postponable,charge_amount);
                        cards_stack.add(charge_card);
                        break;
                    case "ANAR":
                        int box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                        CardGo go_card = new CardGo(postponable,box_nr);
                        cards_stack.add(go_card);
                        break;
                    case "DONAR":
                        CardGive give_card = new CardGive(postponable);
                        cards_stack.add(give_card);
                        break;
                    case "REBRE":
                        CardGet get_card = new CardGet(postponable);
                        cards_stack.add(get_card);
                        break;
                    /**AQUESTES SI Q NO FARIEN FALTA, VERITAT? **/
                }
            }

            cards = cards_stack;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return board;
    }

}


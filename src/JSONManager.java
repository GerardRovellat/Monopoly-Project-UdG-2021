import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gson.*;

public class JSONManager {

    private Board board = new Board();
    private ArrayList<optionalActions> actions = new ArrayList<>();
    private ArrayList<String> star_rewards = new ArrayList<>();
    private Stack<Card> cards = new Stack();
    private int initial_money = 0;
    private String rules_file;
    private String board_file;

    /**
     * @brief $$$$
     * @pre true
     * @post Create JsonManager class with name of files
     */
    public JSONManager(String rules, String board) {
        rules_file = rules;
        board_file = board;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Returns the Monopoly game with configurations from rules and board files
     */
    public Monopoly readFile() {
        this.board = readBoard();
        this.actions = readRules();
        Monopoly monopoly = new Monopoly(board,actions,cards,initial_money,star_rewards);
        return monopoly;
    }

    /**
     * @brief $$$$
     * @pre true
     * @post Write the development file of the game
     */
    public void writeFile() {

    }

    /**
     * @brief $$$$
     * @pre true
     * @post Read the rules file
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
                start_box_rewards_list.add(array_element.toString());
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
     * @brief $$$$
     * @pre true
     * @post Read the board file
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
                        CardPay pay_card = new CardPay(box_action, false, pay_amount);
                        directComand pay_direct_command = new directComand(box_nr,"DIRECTCOMMAND","CARDPAY",pay_card);
                        board.addBox(pay_direct_command);
                        break;
                    case "MULTA":
                        int fine_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardFine fine_card = new CardFine(box_action, false, fine_amount);
                        directComand fine_direct_command = new directComand(box_nr,"DIRECTCOMMAND","CARDFINE",fine_card);
                        board.addBox(fine_direct_command);
                        break;
                    case "COBRAR":
                        int charge_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardCharge charge_card = new CardCharge(box_action, false, charge_amount);
                        directComand charge_direct_command = new directComand(box_nr,"DIRECTCOMMAND","CARDGHARGE",charge_card);
                        board.addBox(charge_direct_command);
                        break;
                    case "ANAR":
                        int go_box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                        CardGo go_card = new CardGo(box_action, false, go_box_nr);
                        directComand go_direct_command = new directComand(box_nr,"DIRECTCOMMAND","CARDGO",go_card);
                        board.addBox(go_direct_command);
                        break;
                    case "DONAR":
                        CardGive give_card = new CardGive(box_action, false);
                        directComand give_direct_command = new directComand(box_nr,"DIRECTCOMMAND","CARDGIVE",give_card);
                        board.addBox(give_direct_command);
                        break;
                    case "REBRE":
                        CardGet get_card = new CardGet(box_action, false);
                        directComand get_direct_command = new directComand(box_nr,"DIRECTCOMMAND","CARDGET",get_card);
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
                Box box = new Box(array_element.getAsInt(),"LUCK");
                board.addBox(box);
            }

            JsonArray luck_cards = j_object.get("targetesSort").getAsJsonArray();
            Stack cards_stack = new Stack<Card>();
            for(JsonElement array_element : luck_cards){
                String card_action = array_element.getAsJsonObject().get("accio").getAsString();
                boolean postponable = array_element.getAsJsonObject().get("posposable").getAsBoolean();
                switch (card_action){
                    case "PAGAR":
                        int pay_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardPay pay_card = new CardPay(card_action,postponable,pay_amount);
                        cards_stack.push(pay_card);
                        break;
                    case "MULTA":
                        int fine_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardFine fine_card = new CardFine(card_action,postponable,fine_amount);
                        cards_stack.push(fine_card);
                        break;
                    case "COBRAR":
                        int charge_amount = array_element.getAsJsonObject().get("quantitat").getAsInt();
                        CardCharge charge_card = new CardCharge(card_action,postponable,charge_amount);
                        cards_stack.push(charge_card);
                        break;
                    case "ANAR":
                        int box_nr = array_element.getAsJsonObject().get("numCasella").getAsInt();
                        CardGo go_card = new CardGo(card_action,postponable,box_nr);
                        cards_stack.push(go_card);
                        break;
                    case "DONAR":
                        CardGive give_card = new CardGive(card_action,postponable);
                        cards_stack.push(give_card);
                        break;
                    case "REBRE":
                        CardGet get_card = new CardGet(card_action,postponable);
                        cards_stack.push(get_card);
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


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONManager {

    private Monopoly monopoly;
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
        Monopoly monopoly;



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
    private void readRules() {
        //Start Gson variables.
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        FileReader reader;
        try {
            //Parse file to JsonElement data and get as JsonObject.
            reader = new FileReader(rules_file);
            JsonElement data = parser.parse(reader);
            JsonObject j_object = data.getAsJsonObject();

            //First element "modalitat"
            String mode = j_object.get("modalitat").getAsString();

            //Second element array "accionsNoAplicables"
            JsonArray restricted_actions = j_object.get("accionsNoAplicables").getAsJsonArray();
            List restricted_action_list = new ArrayList<String>();
            for (JsonElement array_element : restricted_actions) {
                restricted_action_list.add(array_element.toString());
            }

            //Third element "dinersInicials"
            int initial_money = j_object.get("dinersInicials").getAsInt();

            //Fourth element array "recompensesCasellaSortida"
            JsonArray start_box_rewards = j_object.get("recompensesCasellaSortida").getAsJsonArray();
            List start_box_rewards_list = new ArrayList<String>();
            for (JsonElement array_element : start_box_rewards) {
                start_box_rewards_list.add(array_element.toString());
            }

            //Fifth element "percentatgePreuEdificisHipoteca"
            int percentage_mortgage_buildings = j_object.get("percentatgePreuEdificisHipoteca").getAsInt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        try {
            //Parse file to JsonElement data and get as JsonObject.
            reader = new FileReader(rules_file);
            JsonElement data = parser.parse(reader);
            JsonObject j_object = data.getAsJsonObject();

            int boxes_nr = j_object.get("nombreCaselles").getAsInt();

            JsonArray field_boxes = j_object.get("casellesTerreny").getAsJsonArray();
            for(JsonElement array_element : field_boxes){

                int box_nr = j_object.get("numCasella").getAsInt();
                String box_name = j_object.get("nom").getAsString();
                int box_price = j_object.get("preu").getAsInt();
                int box_mortgage = j_object.get("preuHipoteca").getAsInt();
                String box_group = j_object.get("agrupacio").getAsString();
                int box_basic_rent = j_object.get("preuLloguerBasic").getAsInt();
                int box_group_rent = j_object.get("preuLloguerAgrupacio").getAsInt();
                String box_buildable = j_object.get("construible").getAsString();
                int box_max_houses = j_object.get("maxCases").getAsInt();
                int box_houses_price = j_object.get("preuCases").getAsInt();
                boolean box_hotel = j_object.get("hotel").getAsBoolean();
                int box_hotel_price = j_object.get("preuHotel").getAsInt();
                JsonArray houses_rents = j_object.get("lloguerAmbCases").getAsJsonArray();
                List houses_rents_list = new ArrayList<Integer>();
                for(JsonElement rents : field_boxes){
                    houses_rents_list.add(rents.getAsInt());
                }
                int hotel_rent = j_object.get("lloguerAmbHotel").getAsInt();

                //Afegir a board*******.

            }
            JsonArray jail_boxes = j_object.get("casellesTerreny").getAsJsonArray();
            for(JsonElement array_element : jail_boxes){
                int box_nr = j_object.get("numCasella").getAsInt();
                String box_name = j_object.get("nom").getAsString();

                //AFEGIR PRESO ?????????????????????? ***************
            }

            JsonArray direct_command_boxes = j_object.get("casellesComandaDirecta").getAsJsonArray();
            for(JsonElement array_element : direct_command_boxes){
                int box_nr = j_object.get("numCasella").getAsInt();
                String box_action = j_object.get("accio").getAsString();
                if(box_action.equals("MULTA")){ int box_amount = j_object.get("quantitat").getAsInt(); }

                //AFEGIR a board **********.
            }

            JsonArray bet_boxes = j_object.get("casellesAposta").getAsJsonArray();
            List bet_boxes_list = new ArrayList<Integer>();
            for(JsonElement array_element : bet_boxes){
                bet_boxes_list.add(array_element.getAsInt());

                //AFEGIR a board ?????????????????????? ***************
            }

            JsonArray luck_cards_boxes = j_object.get("casellesSort").getAsJsonArray();
            List luck_cards_boxes_list = new ArrayList<Integer>();
            for(JsonElement array_element : bet_boxes){
                luck_cards_boxes_list.add(array_element.getAsInt());

                //AFEGIR a board ?????????????????????? ***************
            }

            JsonArray luck_cards = j_object.get("targetesSort").getAsJsonArray();
            for(JsonElement array_element : bet_boxes){
                String card_action = j_object.get("accio").getAsString();
                boolean postponable = j_object.get("posposable").getAsBoolean();
                if (!postponable){ int card_amount = j_object.get("quantitat").getAsInt(); }

                //AFEGIR a board ?????????????????????? ***************
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


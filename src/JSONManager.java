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
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        FileReader fr;


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
    private void readBoard() {
        //Start Gson variables.
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        FileReader reader;
        try {
            //Parse file to JsonElement data and get as JsonObject.
            reader = new FileReader(rules_file);
            JsonElement data = parser.parse(reader);
            JsonObject j_object = data.getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


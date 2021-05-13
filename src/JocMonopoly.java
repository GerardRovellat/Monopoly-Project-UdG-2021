/**
 * @author Gerard Rovellat
 * @file JocMonopoly.java
 * @class JocMonopoly (main)
 * @brief Main principal del joc del Monopoly.
 */
public class JocMonopoly {

    /**
     * @brief Main principal per començar el Joc de Monopoly.
     * @pre \p true
     * @post Joc acabat
     * @param args fitxers d'entrada
     */
    public static void main(String[] args){

        JSONManager json_manager = new JSONManager(args[0],args[1]);
        Monopoly monopoly = json_manager.readFile();
        System.out.println("llegit");

        monopoly.play();
    }

}

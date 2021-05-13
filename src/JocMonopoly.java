/**
 * @author Gerard Rovellat
 * @file CardGo.java
 * @class CardGo
 * @brief Targeta que mou al jugador a una casella determinada en el tauler de Monopoly.
 */
public class JocMonopoly {

    public static void main(String[] args){

        JSONManager json_manager = new JSONManager(args[0],args[1]);
        Monopoly monopoly = json_manager.readFile();
        System.out.println("llegit");

        monopoly.play();
    }

}

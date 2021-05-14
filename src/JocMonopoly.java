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
        try{
            if(args.length == 1){
                if(args[1].equals("-h")){
                    System.out.println("------------------- AJUDA DE EXECUCIÓ DE L'APLICACIÓ -------------------");
                    System.out.println("\t-h: Mostra l'ajuda per la inicialització de l'aplicació");
                    System.out.println("\t-s: Obra un selector grafic per poder obrir el joc i comença la execució");
                    System.out.println("\tfitxer_regles fitxer_tauler: Llegeix els dos fitxers passats per parametre" +
                            " respectivament per inicialitzar el joc del Monopoly. ");
                }
                else if(args[1].equals("-s")){
                    //FER SELECTOR GRÀFIC
                    //JSONManager json_manager = new JSONManager(args[0],args[1]);
                    //Monopoly monopoly = json_manager.readFile();
                    //monopoly.play();
                }
                else System.err.println("Parametres entrats incorrectes, la aplicació es tancara...");
            }
            else if(args.length == 2){
                JSONManager json_manager = new JSONManager(args[0],args[1]);
                Monopoly monopoly = json_manager.readFile();
                monopoly.play();
            }
        }
        catch (Exception e){
            System.err.println("S'ha produït una excepció");
            e.printStackTrace();
        }
    }


}

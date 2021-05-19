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
     * @post Joc acabat.
     * @param args fitxers d'entrada
     */
    public static void main(String[] args){
        try{
            if(args.length == 1){
                if(args[0].equals("-h")){
                    printHelp();
                }
                else if(args[0].equals("-s")){
                    UIFileSelector fileSelector = new UIFileSelector();
                    fileSelector.setVisible(true);
                    while(fileSelector.getStatus() != true) Thread.sleep(100);
                    JSONManager json_manager = new JSONManager(fileSelector.getRulesFileName(),fileSelector.getBoardFileName());
                    Monopoly monopoly = json_manager.readFile();
                    if(!monopoly.getMode().equals("classica")) System.out.println("No es pot jugar amb aquest mode...");
                    else monopoly.play();
                }
                else System.err.println("Parametres entrats incorrectes, la aplicació es tancara...");
            }
            else if(args.length == 2){
                JSONManager json_manager = new JSONManager(args[0],args[1]);
                Monopoly monopoly = json_manager.readFile();
                if(!monopoly.getMode().equals("classica")) System.out.println("No es pot jugar amb aquest mode...");
                else monopoly.play();
            }
        }
        catch (Exception e){
            System.err.println("ERROR: S'ha produït una excepció");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static private void printHelp(){
        System.out.println("------------------- AJUDA DE EXECUCIÓ DE L'APLICACIÓ -------------------");
        System.out.println("Utilització: java -jar JocMonopoly.jar [Fitxer_regles] [Fitxer_tauler]");
        System.out.println("\t-h: Mostra l'ajuda per la inicialització de l'aplicació");
        System.out.println("\t-s: Obra un selector grafic per poder obrir el joc i comença la execució");
        System.out.println("\t-Fitxer_regles: Fitxer tipus JSON de la configuració de regles del Monopoly.");
        System.out.println("\t-Fitxer_tauler: Fitxer tipus JSON de la configuració del tauler del Monopoly");
    }

}


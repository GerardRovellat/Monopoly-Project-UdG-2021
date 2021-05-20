import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Gerard Rovellat
 * @file OutputManager.java
 * @class OutputManager
 * @brief Classe que s'encarrega de gestionar les sortides en el nostre cas al fitxer de desenvolupament de partida.
 */
public class OutputManager {

    private File dev_file;          ///< Fitxer desenvolupament de la partida

    /**
     * @brief Constructor OutputManager. Crea un directori \p saves si no esta creat i crea un fitxer de desenvolupament de partida amb nom.
     * logs_x on x es un numero correlatiu.
     * @pre \p true
     * @post El fitxer de logs ha estat creat.
     */
    public OutputManager(){
        File directory = new File("saves");
        if(!directory.exists()) directory.mkdir();
        int index = 1;
        dev_file = new File ("saves","logs_"+index+".txt");
        while(dev_file.exists()){
            index++;
            dev_file = new File("saves","logs_"+index+".txt");
        }
        try{
            dev_file.createNewFile();
        }
        catch (IOException e){
            System.out.println("ERROR: No s'ha pogut crear el fitxer de desenvolupament de la partida.");
        }
    }

    /**
     * @brief Escriu text \p line al fitxer de desenvolupament de partida \p dev_file.
     * @pre \p true
     * @post la linea de text \p line ha estat escrita a \p dev_file.
     * @param line text que s'ha d'escriure al fitxer.
     */
    public void fileWrite(String line){
        try{
            FileWriter fr = new FileWriter(dev_file,true);
            fr.write(line+"\n");
            fr.close();
        } catch (IOException e){System.out.println("No s'ha pogut escriure en el fitxer de desenvolupament de partida");}
    }
}

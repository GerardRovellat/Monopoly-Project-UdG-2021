import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputManager {

    private File dev_file;

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
            System.out.println("ERROR: No s'ha pogut crear el fitxer de desenvolupament de la partida.");;
        }
    }

    private void fileWrite(String line){
        try{
            FileWriter fr = new FileWriter(dev_file,true);
            fr.write(line);
            fr.close();
        } catch (IOException e){System.out.println("No s'ha pogut escriure en el fitxer de desenvolupament de partida");}
    }
}

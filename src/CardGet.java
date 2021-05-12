import java.util.ArrayList;
import java.util.Scanner;

public class CardGet extends Card{

    public CardGet (boolean postposable) {
        super("GET",postposable);
    }

    public void execute(ArrayList<Player> players, Board board, Player current_player) {
        Scanner scan = new Scanner(System.in);
        System.out.println("El jugador rebra una propietats d'algun dels seus adversaris");
        int option_nr = 0;
        ArrayList<Integer> not_disponible = new ArrayList<Integer>();
        for (Player player : players){
            if (current_player != player && player.haveFields() ) {
                System.out.println(option_nr + "- " + player.getName());
            }
            else{
                not_disponible.add(option_nr);
            }
            option_nr++;
        }
        System.out.println("Seleccioni el jugador");
        option_nr = scan.nextInt();
        while(not_disponible.contains(option_nr)){
            System.out.println("Error, sel·leccioni un jugador de la llista");
        }
        Player choosed = players.get(option_nr);
        int field_nr = 0;
        for(Field field : choosed.getFields()){
            System.out.println(field_nr+"- "+field.getName()+" ("+field.getPrice()+")");
        }
        System.out.println("Seleccioni un terreny");
        while(field_nr < 0 && field_nr > choosed.getFields().size()){
            System.out.println("Error, sel·leccioni un terreny correcte");
        }
        Field field_choosed = choosed.getFields().get(field_nr);
        current_player.addBox(field_choosed);
        choosed.removeBox(field_choosed);
        System.out.println("En "+current_player.getName()+" ha adquirit "+field_choosed.getName());
    }

    public String toString(){
        return "El jugador rebra una propietats d'algun dels seus adversaris";
    }
}

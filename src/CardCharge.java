import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CardCharge extends Card{
    private int quantity;
    private String message;
    private HashMap<Integer,String> messages = new HashMap<>();

    public CardCharge (boolean postposable, int quantity) {
        super("CHARGE",postposable);
        this.quantity = quantity;
        messages.put(0,"VENDES TU BILLETE DE TREN PARA VALLADOLID EN UNA SUBASTA POR INTERNET RECIBE " + quantity + " €");
        messages.put(1,"¡HAS GANADO EL BOTE DE LA LOTERÍA RECIBE " + quantity + " €");
        messages.put(2,"BENEFICIO POR LA VENTA DE SUS ACCIONES RECIBE " + quantity + " €");
        messages.put(3,"TU COMPAÑIA DE INTERNET OBTIENE BENEFICIOS RECIBE " + quantity + " €");
        messages.put(4,"TUS COMPAÑEROS TE ALQUILAS TU VILLA EN CANNES DURANTE UNA SEMANA COBRA " + quantity + " €");
        messages.put(5,"GANAS UN CONCURSO DE KARAOKE EN TELEVISIÓN COBRA " + quantity + "€ POR UN CONTRATO DISCOGRÁFICO");
        messages.put(6,"HEREDAS 3M € PERO LOS GRAVÁMENES SON MUY ALTOS RECIBE " + quantity + " €");
        messages.put(7,"COMPRAS UNA ACUARELA EN MELILLA Y LA VENDES POS MÁS DEL DOBLE RECIBE " + quantity + " €");
        messages.put(8,"GANAS LA DEMANDA DE TU SEGURO DE COCHE RECIBE " + quantity + " €");
        messages.put(9,"RECIBE " + quantity + " € DE BENEFICIOS POR ALQUILAR LOS SERVICIOS DE TU JET PRIVADO");
        messages.put(10,"DEVOLUCIÓN DE HACIENDA COBRA " + quantity + " €");
        messages.put(11,"HAS GANADO TU APUESTA EN LA ELIMINACIÓN DEL MADRID EN CHAMPIONS COBRA " + quantity + " €");
    }

    public void execute(Board board, Player current_player) {
        Random rand = new Random();
        message = messages.get(rand.nextInt(messages.size()) - 1);
        System.out.println(message);
        current_player.charge(quantity);
        System.out.println("DINERS REBUTS");
    }

    public int getQuantity() { return this.quantity; }

    public String toString(){
        return message;
    }
}

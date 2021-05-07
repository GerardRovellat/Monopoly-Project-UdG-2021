import java.util.ArrayList;

public class Buy implements optionalActions{

    private ArrayList<Player> players_list = new ArrayList<>();

    public Buy() {};

    @Override // Heretat d'Object
    public String toString() {
        return "Comprar: Fer una oferta de compra a un altre jugador";
    }

    public void execute() {
        /*
            preguntar oferta de compra;
            Pregunta al jugador propietari si accepta;
            while() { regateig; }
            Si true {
                Jugador propietari = jugador que ven;
                restar pasta al actual
                afegir terreny al actual
                sumar pasta al propietari;
                restar terreny al propietari;
            }
         */
    }
}
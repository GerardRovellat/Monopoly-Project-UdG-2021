import java.util.ArrayList;
import java.util.Scanner;

public class Buy implements optionalActions{


    public Buy() {

    };

    @Override // Heretat d'Object
    public String toString() {
        return "Comprar: Fer una oferta de compra a un altre jugador";
    }

    public void execute(ArrayList<Player> players,Player actual_player) {
        Scanner scan = new Scanner(System.in);
        System.out.println("PROPIETATS DELS JUGADORS");
        for (Player aux : players) {
            System.out.println(aux.toString());
        }
        System.out.println("A QUIN JUGADOR L'HI VOL FER LA OFERTA?:");
        int cont = 0;
        for (Player aux : players) {
            if (aux.getName() != actual_player.getName()) {
                System.out.println(cont + ". " + aux.getName());
            }
            cont++;
        }
        System.out.println("Selecioni la opcio que desitgi:");
        int value = -1;
        while (value < 0 || value > cont) {
            value = scan.nextInt();
            if (value < 0 || value > cont) System.out.println("La opcio entrada es incorrecte ( ha de ser un enter entre 0 i " + cont + " ). Torni a provar:");
        }
        if (value == -1) ;//Throw error
        Player buy_player = players.get(value);
        ArrayList<Field> fields = buy_player.getFields();
        if (fields.size()!=0) {
            System.out.println("Selecioni la propietat de " + buy_player.getName() + " que desitja comprar:");
            cont = 0;
            for (Field aux : fields) {
                System.out.println(cont + ". " + aux.getName() + " | " + aux.getPrice());
                cont++;
            }
            System.out.println("Selecioni la opcio que desitgi:");
            value = -1;
            while (value < 0 || value > cont) {
                value = scan.nextInt();
                if (value < 0 || value > cont)
                    System.out.println("La opcio entrada es incorrecte ( ha de ser un enter entre 0 i " + cont + " ). Torni a provar:");
            }
            Field buy_field = fields.get(value);
            System.out.println("Indiqui la oferta inicial:");
            int actual_offer = 0; // -1 if not
            actual_offer = scan.nextInt();
            System.out.println("Oferta realitzada.");
            System.out.println("Comença la negociació");
            boolean buy_final = false;
            System.out.println(buy_player.getName() + "> COMPRAR " + buy_field.getName() + " " + actual_offer);
            Player offer_active_player = buy_player;
            while (!buy_final) {
                String tmp ;
                System.out.println(offer_active_player.getName() + ": indiqui ok si accepta la oferta, no si la rebutja o un valor per fer una contraoferta:");
                tmp = scan.next();
                if (tmp == "ok") {
                    buy_final = true;
                } else if (tmp == "no") {
                    buy_final = true;
                    actual_offer = -1;
                } else if (Integer.parseInt(tmp) < 0) {
                    System.out.println("Valor incorrecte, la oferta ha de superior a 0");
                } else {
                    actual_offer = Integer.parseInt(tmp);
                }
                if (offer_active_player == buy_player) offer_active_player = actual_player;
                else offer_active_player = buy_player;
            }
            if (actual_offer == -1)
                System.out.println("Les negociacions no han concuit i per tant la compra no es farà efectiva");
            else {
                actual_player.pay(actual_offer);
                buy_player.charge(actual_offer);
                actual_player.addBox(buy_field);
                System.out.println("La venda s'ha fet efectiva, per un preu de " + actual_offer + "€");
            }
        }
        else System.out.println("Aquest jugador no te cap propietat en posesió per poder comprar");
    }
}
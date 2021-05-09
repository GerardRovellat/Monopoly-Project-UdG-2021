import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Sell implements optionalActions{

    public Sell() {};

    @Override // Heretat d'Object
    public String toString() {

        return "Vendre: posar a la venda una de les teves propietats, incloent-hi els edificis que hi pugui haver-hi.";
    }

    public boolean execute(ArrayList<Player> players,Player actual_player,Movement m) {
        boolean is_possible = true;
        if(actual_player.getFields().isEmpty()){
            System.out.println("Cap terreny en propietat");
            is_possible = false;
        }
        else {
            System.out.println("Llistat de terrenys en propietat:");
            int field_nr = 0;
            for (Field field : actual_player.getFields()) {
                System.out.println("\t" + field_nr + "- " + field.getName() + " (Valor del terreny " + field.getPrice() + "€)");
                field_nr++;
            }
            Scanner scan = new Scanner(System.in);
            int confirm = 2;
            Field field_to_sell = null;
            while (confirm == 2) {
                System.out.println("Quin terreny vols vendre?");
                field_nr = scan.nextInt();
                field_to_sell = actual_player.getFields().get(field_nr);
                System.out.println(field_to_sell.toString());
                System.out.println("Segur que vols vendre aquesta propietat? (1-Si 2-No 0-Cancel·lar)");
                confirm = scan.nextInt();
                while (confirm != 1 && confirm != 2 && confirm != 0) {
                    System.out.println("Tria una opcio correcte");
                    confirm = scan.nextInt();
                }
            }
            if(confirm == 1) {
                int sell_price;
                System.out.println("Per quin preu vols començar la venta?");
                sell_price = scan.nextInt();
                System.out.println("La subhasta per " + field_to_sell.getName() + " comença per " + sell_price + "€");
                ArrayList<Player> auction_players;
                auction_players = players;
                auction_players.remove(actual_player);
                int offer, max_offer = -1;
                Player winner = null;
                while (auction_players.size() > 1) {
                    for (Player aux_player : auction_players) {
                        String name_of_player = aux_player.getName();
                        int actual_money = aux_player.getMoney();
                        System.out.println(name_of_player + " tens " + actual_money + "€");
                        System.out.println("Que ofereixes? (-1 per retirar-se):");
                        offer = scan.nextInt();
                        while (actual_money < offer && offer != -1) {
                            System.out.println("Erroni, no pots pagar més de " + actual_money + ", prova de nou");
                            offer = scan.nextInt();
                        }
                        if (offer == -1) {
                            auction_players.remove(aux_player);
                            System.out.println(name_of_player + " s'ha retirat de la subhasta");
                        } else {
                            if (max_offer < offer) {
                                max_offer = offer;
                                winner = aux_player;
                            }
                        }
                    }
                }
                if (winner == null) {
                    System.out.println("Cap jugador ha comprat la propietat " + field_to_sell.getName());
                } else {
                    winner.pay(max_offer);
                    actual_player.charge(max_offer);
                    System.out.println("El jugador " + winner + " ha comprat " + field_to_sell + " per " + max_offer);
                }
            }
            else{
                is_possible = false;
                System.out.println("S'ha cancel·lat la compra");
            }
        }
        return  is_possible;
    }

}
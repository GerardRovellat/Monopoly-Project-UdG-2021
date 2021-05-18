import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Gerard Rovellat
 * @file Sell.java
 * @class Sell
 * @brief Implementa la interifíce d'optionalActions. Aquesta implementació permet a un jugador vendre una de les
 * seves propietats a un dels altres jugadors contraris. Aquesta venda és fara en format de subhasta.
 */

public class Sell implements optionalActions{

    /**
     * @brief Constructor de Sell.
     * @pre \p true
     * @post Crea una acció opcional Sell.
     */
    public Sell() {};

    /**
     * @brief toString per mostrar la descripció de l'acció Sell per text.
     * @pre \p true
     * @post Mostra la descripció de Sell
     * @return String de la sortida per pantalla de Sell
     */
    @Override // Heretat d'Object
    public String toString() {
        return "Vendre: posar a la venda una de les teves propietats, incloent-hi els edificis que hi pugui haver-hi.";
    }

    /**
     * @brief Mètode per executar el procés de vendre un terreny en propietat d'un jugador.
     * @pre Jugador \p current_player != null i Moviment \p m != null
     * @post Una propietat del jugador ha estat venuda al guanyador de la subhasta, en cas contrari no ha estat venuda
     * i la subhasta no té guanyador.
     * @param players ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player Jugador que fa la venda.
     * @param m Moviment que crida Sell, en aquesta implementació, \p m no és usada però s'ha de passar.
     * @return \p true si el procés s'ha realitzat, \p false altrament.
     */
    public boolean execute(ArrayList<Player> players,Player current_player,Movement m) {
        OutputManager output = m.getOutput();
        boolean is_possible = true;
        if(current_player.getFields().isEmpty()){
            System.out.println("Cap terreny en propietat");
            is_possible = false;
        }
        else {
            System.out.println("Llistat de terrenys en propietat:");
            int field_nr = 0;
            for (Field field : current_player.getFields()) {
                System.out.println("\t" + field_nr + "- " + field.getName() + " (Valor del terreny " + field.getPrice() + "€)");
                field_nr++;
            }
            Scanner scan = new Scanner(System.in);
            int confirm = 1, nr_of_fields = field_nr;
            Field field_to_sell = null;
            while (confirm == 1) {
                System.out.println("Quin terreny vols vendre?");
                field_nr = current_player.optionSelection("sellFieldSelect", current_player, null, null, null, null, 0,null);
                while (field_nr < 0 || field_nr > nr_of_fields - 1) {
                    System.out.println("Tria una opcio correcte:");
                    field_nr = current_player.optionSelection("confirmation", null, null, null, null, null, 0,null);
                }
                field_to_sell = current_player.getFields().get(field_nr);
                System.out.println(field_to_sell.toString());
                System.out.println("Segur que vols vendre aquesta propietat? (0-Si 1-No 2-Cancel·lar)");
                confirm = current_player.optionSelection("confirmation", null, null, null, null, null, 0,null);
                while (confirm != 0 && confirm != 1 && confirm != 2) {
                    System.out.println("Tria una opcio correcte:");
                    confirm = current_player.optionSelection("confirmation", null, null, null, null, null, 0,null);
                }
            }
            if(confirm == 0) {
                int sell_price;
                System.out.println("Per quin preu vols començar la venta?");
                sell_price = current_player.optionSelection("sellInitalOffer",current_player,field_to_sell,null,null,null,0,null);
                System.out.println("La subhasta per " + field_to_sell.getName() + " comença per " + sell_price + "€");
                output.fileWrite(current_player.getName() + "> Ven " + field_to_sell.getName() + " per " + sell_price + "€");
                ArrayList<Player> auction_players = new ArrayList<>();
                ArrayList<Player> retired_players = new ArrayList<>();
                auction_players.addAll(players);
                auction_players.remove(current_player);
                int offer = sell_price, max_offer = sell_price;
                Player winner = null;
                if (auction_players.size() == 1){
                    String name_of_player = auction_players.get(0).getName();
                    int current_money = auction_players.get(0).getMoney();
                    System.out.println(name_of_player + " tens " + current_money + "€");
                    System.out.println("Que ofereixes? (-1 per retirar-se):");
                    offer = auction_players.get(0).optionSelection("sellBuyerOffer", auction_players.get(0), field_to_sell, null, null, null, offer,null);
                    while (current_money < offer || offer < max_offer && offer != -1) {
                        if (offer < max_offer)
                            System.out.println("Erroni, s'ha de superar el import de " + max_offer);
                        else
                            System.out.println("Erroni, no pots pagar més de " + current_money + ", prova de nou");
                            offer = auction_players.get(0).optionSelection("sellBuyerOffer", auction_players.get(0), field_to_sell, null, null, null, offer,null);
                    }
                    if (offer == -1) {
                        retired_players.add(auction_players.get(0));
                        System.out.println(name_of_player + " s'ha retirat de la subhasta");
                        output.fileWrite("\t" +name_of_player + "> Es retira");
                    } else {
                        winner = auction_players.get(0);
                    }
                }
                else {
                    while (auction_players.size() > 1) {
                        for (Player aux_player : auction_players) {
                            if (auction_players.size() - retired_players.size() != 1 || winner == null) {
                                String name_of_player = aux_player.getName();
                                int current_money = aux_player.getMoney();
                                System.out.println(name_of_player + " tens " + current_money + "€");
                                System.out.println("Que ofereixes? (-1 per retirar-se):");
                                offer = aux_player.optionSelection("sellBuyerOffer", aux_player, field_to_sell, null, null, null, offer,null);
                                while (current_money < offer || offer < max_offer && offer != -1) {
                                    if (offer < max_offer)
                                        System.out.println("Erroni, s'ha de superar el import de " + max_offer);
                                    else
                                        System.out.println("Erroni, no pots pagar més de " + current_money + ", prova de nou");
                                    offer = aux_player.optionSelection("sellBuyerOffer", aux_player, field_to_sell, null, null, null, offer,null);
                                }
                                if (offer == -1) {
                                    retired_players.add(aux_player);
                                    System.out.println(name_of_player + " s'ha retirat de la subhasta");
                                    output.fileWrite("\t" + name_of_player + "> Es retira");
                                } else {
                                    if (max_offer < offer) {
                                        max_offer = offer;
                                        winner = aux_player;
                                    }
                                    output.fileWrite("\t" + name_of_player + "> Ofereix " + offer + "€");
                                }
                            }
                        }
                        for (Player retired : retired_players) {
                            auction_players.remove(retired);
                        }
                        retired_players.clear();
                    }
                }
                if (winner == null) {
                    System.out.println("Cap jugador ha comprat la propietat " + field_to_sell.getName());
                    output.fileWrite(current_player.getName() + "> Cap jugador compra");
                }
                else {
                    winner.pay(max_offer);
                    current_player.charge(max_offer);
                    System.out.println("El jugador " + winner + " ha comprat " + field_to_sell.getName() + " per " + max_offer);
                    output.fileWrite(winner.getName() + "> Compra " + field_to_sell.getName() + " a " + current_player.getName() + " per " + max_offer);
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
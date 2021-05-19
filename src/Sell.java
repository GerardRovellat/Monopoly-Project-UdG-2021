import javafx.util.Pair;
import java.util.ArrayList;


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
     * @param players           ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player    Jugador que fa la venda.
     * @param m                 Moviment que crida Sell, en aquesta implementació, \p m no és usada però s'ha de passar.
     * @return \p true si el procés s'ha realitzat, \p false altrament.
     */
    public boolean execute(ArrayList<Player> players,Player current_player,Movement m) {
        OutputManager output = m.getOutput();
        boolean is_possible = true;
        if(current_player.getFields().isEmpty()){
            System.out.println("Cap terreny en propietat");
            output.fileWrite(current_player.getName() + "> No te cap terreny en propietat");
            is_possible = false;
        }
        else {
            Field field_to_sell = selectFieldToSell(current_player);
            if(field_to_sell != null) {
                int sell_price = selectSellPrice(current_player,field_to_sell,output);
                Pair<Player,Integer> winner = doAuction(players,current_player,field_to_sell,output,sell_price);
                if (winner.getKey() == null) {
                    System.out.println("Cap jugador ha comprat la propietat " + field_to_sell.getName());
                    output.fileWrite(current_player.getName() + "> Cap jugador compra");
                }
                else {
                    winner.getKey().pay(winner.getValue());
                    current_player.charge(winner.getValue());
                    System.out.println("El jugador " + winner.getKey().getName() + " ha comprat " + field_to_sell.getName() + " per " + winner.getValue());
                    output.fileWrite(winner.getKey().getName() + "> Compra " + field_to_sell.getName() + " a " + current_player.getName() + " per " + winner.getValue());
                }
            }
            else{
                is_possible = false;
                System.out.println("S'ha cancel·lat la compra");
            }
        }
        return  is_possible;
    }

    /**
     * @brief Metode per realitzar la subhasta a l'hora de vendre.
     * @pre Jugador \p field_to_sell != null i Moviment \p sell_price != 0
     * @post La subhasta ha estat realitzada
     * @param players           ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player    Jugador que fa la venda.
     * @param field_to_sell     Terreny el qual sera venut.
     * @param output            Fitxer per escriure desenvolupament de partida
     * @param sell_price        Preu inicial de la venta
     * @return Retorna el guanyador de la subhasta i el preu pel qual l'ha comprat.
     */
    private Pair<Player,Integer> doAuction(ArrayList<Player> players, Player current_player, Field field_to_sell, OutputManager output, int sell_price){
        ArrayList<Player> auction_players = new ArrayList<>();
        ArrayList<Player> retired_players = new ArrayList<>();
        auction_players.addAll(players);
        auction_players.remove(current_player);
        int offer = sell_price, max_offer = sell_price;
        Player winner = null;
        while (auction_players.size() > 0) {
            for (Player aux_player : auction_players) {
                if (auction_players.size() - retired_players.size() != 1 || winner == null) {
                    String name_of_player = aux_player.getName();
                    int current_money = aux_player.getMoney();
                    System.out.println(name_of_player + " tens " + current_money + "€");
                    System.out.println("Que ofereixes? (-1 per retirar-se):");
                    offer = aux_player.optionSelection("sellBuyerOffer", aux_player, field_to_sell, null, null, null, offer, null);
                    while (current_money < offer || offer < max_offer && offer != -1) {
                        if (offer < max_offer)
                            System.out.println("Erroni, s'ha de superar el import de " + max_offer);
                        else
                            System.out.println("Erroni, no pots pagar més de " + current_money + ", prova de nou");
                        offer = aux_player.optionSelection("sellBuyerOffer", aux_player, field_to_sell, null, null, null, offer, null);
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
            if (auction_players.size() == 1 && winner != null) retired_players.addAll(auction_players);
            for (Player retired : retired_players) {
                auction_players.remove(retired);
            }
            retired_players.clear();
        }
        Pair<Player,Integer> winner_info= new Pair(winner,max_offer);
        return winner_info;
    }

    /**
     * @brief Metode per sel·leccionar el preu de la venta d'un terreny \p field_to_sell.
     * @pre Jugador \p true
     * @post Preu de la venta sel·leccionat.
     * @param current_player    Jugador que fa la venda.
     * @param field_to_sell     Terreny per vendre a seleccionar.
     * @param output            Fitxer de desenvolupament de partida.
     * @return Retorna el preu \p price pel que començara la venta.
     */
    private int selectSellPrice(Player current_player, Field field_to_sell, OutputManager output){
        int price;
        System.out.println("Per quin preu vols començar la venta?");
        price = current_player.optionSelection("sellInitalOffer",current_player,field_to_sell,null,null,null,0,null);
        System.out.println("La subhasta per " + field_to_sell.getName() + " comença per " + price + "€");
        output.fileWrite(current_player.getName() + "> Ven " + field_to_sell.getName() + " per " + price + "€");
        return price;
    }

    /**
     * @brief Mètode per sel·leccionar el terreny que es vol vendre.
     * @pre \p current_player != null
     * @post Propietat a vendre sel·leccionada.
     * @param current_player    Jugador que fa la venda.
     * @return Retorna un terreny \p Field, null en cas de cancel·lar-se.
     */
    private Field selectFieldToSell(Player current_player){
        System.out.println("Llistat de terrenys en propietat:");
        int field_nr = 0;
        for (Field field : current_player.getFields()) {
            System.out.println("\t" + field_nr + "- " + field.getName() + " (Valor del terreny " + field.getPrice() + "€)");
            field_nr++;
        }
        int confirm = 1;
        int nr_of_fields = field_nr;
        Field field_to_sell = null;
        while (confirm == 1) {
            System.out.println("Quin terreny vols vendre?");
            field_nr = current_player.optionSelection("sellFieldSelect", current_player, null, null, null, null, 0, null);
            while (field_nr < 0 || field_nr > nr_of_fields - 1) {
                System.out.println("Tria una opcio correcte:");
                field_nr = current_player.optionSelection("confirmation", null, null, null, null, null, 0, null);
            }
            field_to_sell = current_player.getFields().get(field_nr);
            System.out.println(field_to_sell.toString());
            System.out.println("Segur que vols vendre aquesta propietat? (0-Si 1-No 2-Cancel·lar)");
            confirm = current_player.optionSelection("confirmation", null, null, null, null, null, 0, null);
            while (confirm != 0 && confirm != 1 && confirm != 2) {
                System.out.println("Tria una opcio correcte:");
                confirm = current_player.optionSelection("confirmation", null, null, null, null, null, 0, null);
            }
        }
        if( confirm == 0) return field_to_sell;
        else return null;

    }

}
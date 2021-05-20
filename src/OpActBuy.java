import java.util.ArrayList;

/**
 * @author Marc Got
 * @file Buy.java
 * @class Buy
 * @brief Implementa la interifíce d'optionalActions. Aquesta implementació permet a un jugador comprar una de les
 * propietats de un dels altres jugadors contraris. Aquesta compra és negociable
 */
public class OpActBuy implements optionalActions {

    /**
     * @brief Constructor de Buy.
     * @pre \p true
     * @post Crea una acció opcional Buy.
     */
    public OpActBuy() { }

    /**
     * @return String de la sortida per pantalla de Buy
     * @brief toString per mostrar la descripció de l'acció Buy per text.
     * @pre \p true
     * @post Mostra la descripció de Buy
     */
    @Override // Heretat d'Object
    public String toString() {
        return "Comprar: Fer una oferta de compra a un altre jugador";
    }

    /**
     * @param players        ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player Jugador que fa la compra.
     * @param m              Moviment que crida Buy, en aquesta implementació, \p m no és usada però s'ha de passar.
     * @return \p true si el procés s'ha realitzat, \p false altrament.
     * @brief Mètode per executar el procés de compra un terreny en propietat d'un jugador.
     * @pre Jugador \p current_player != null i Moviment \p m != null
     * @post Una propietat de algun jugador ha estat comprada o la acció s'ha cancelat
     */
    public boolean execute(ArrayList<Player> players, Player current_player, Movement m) {
        OutputManager output = m.getOutput();
        Player buy_player = playerSelect(current_player, players,output);
        if (buy_player != null) {
            BoxField buy_field = fieldSelect(buy_player);
            if (buy_field != null) {
                int initial_offer = startOffer(current_player, buy_field, output);
                return buyNegociation(current_player, buy_player, buy_field, initial_offer, output);
            }
        }
        return false;
    }

    /**
     * @param current_player Jugador que fa la compra.
     * @param players        ArrayList de jugadors que estan jugant al Monopoly.
     * @param output         sortida al fitxer
     * @return el jugador selecionat o null si no n'hi ha cap
     * @brief Mostra tots els jugadors amb terrenys i deixa escollir a un d'ells per comprar-li un terreny
     * @pre true
     * @post El jugador selecionat ha estat retornat, o en la seva absencia, s'ha retorntat un jugador null
     */
    private Player playerSelect(Player current_player, ArrayList<Player> players,OutputManager output) {
        boolean players_available = false;
        for (Player aux : players) {
            if (!aux.getName().equals(current_player.getName()) && aux.getFields().size() > 0) {
                System.out.println(players.indexOf(aux) + ". " + aux.getName());
                players_available = true;
            }
        }
        if (players_available) {
            try {
                System.out.println("Selecioni la opcio que desitgi:");
                while (true) {
                    int value = current_player.optionSelection("buyPlayerSelect", null, null, null, players, null, 0, null);
                    if (value >= 0 && value < players.size()) return players.get(value);
                    else System.out.println("La opcio entrada es incorrecte ( ha de ser un enter entre 0 i " + (players.size() - 1) + " ). Torni a provar:");
                }
            } catch (NumberFormatException e) {System.out.println("Format incorrecte. Torna-hi.");}
        }
        output.fileWrite(current_player.getName() + "> Cap jugador amb propietats");
        System.out.println("No hi ha cap jugador amb propietats per comprar");
        return null;
    }

    /**
     * @param buy_player     Jugador que ven el terreny
     * @return el terreny selecionat per el jugador o null si no es pot
     * @brief Mostra tots els terreyns del jugador i deixa escollir a un d'ells per comprar'l-ho
     * @pre true
     * @post El terreny selecionat ha estat retornat, o en la seva absencia, s'ha retorntat un terreny null
     */
    private BoxField fieldSelect(Player buy_player) {
        ArrayList<BoxField> fields = buy_player.getFields();
        if (fields.size() != 0) {
            System.out.println("Selecioni la propietat de " + buy_player.getName() + " que desitja comprar:");
            for (BoxField aux : fields) System.out.println(fields.indexOf(aux) + ". " + aux.getName() + " | " + aux.getPrice());
            System.out.println("Selecioni la opcio que desitgi:");
            try {
                while (true) {
                    int value = buy_player.optionSelection("buyFieldSelect", null, null, null, null, null, 0, null);
                    if (value >= 0 && value < fields.size()) return fields.get(value);
                    else System.out.println("La opcio entrada es incorrecte ( ha de ser un enter entre 0 i " + (fields.size() - 1) + " ). Torni a provar:");
                }
            } catch (NumberFormatException e) {System.out.println("Format incorrecte. Torna-hi.");}
        } else System.out.println("Aquest jugador no te cap propietat en posesió per poder comprar");
        return null;
    }

    /**
     * @param current_player Jugador que fa la compra.
     * @param buy_field      Terreny a comprar
     * @param output         sortida al fitxer
     * @return el valor de la oferta inicial o un -1 en cas de error (no tractat ja que no arriba mai a retornnar-lo
     * @brief Demana al jugador el valor de la seva oferta inicial
     * @pre true
     * @post El valor de la oferta ha estat retornat i el valor > 0 i < la quantitat de diners del jugador comprador
     */
    private int startOffer(Player current_player, BoxField buy_field, OutputManager output) {
        int current_offer;
        System.out.println("Indiqui la oferta inicial:");
        try {
            while (true) {
                current_offer = current_player.optionSelection("buyInitalOffer", null, buy_field, null, null, null, 0, null);
                if (current_offer > 0 || current_offer <= current_player.getMoney()) {
                    output.fileWrite(current_player.getName() + "> Vol comprar " + buy_field.getName() + " per " + current_offer + "€");
                    System.out.println("Oferta realitzada.");
                    return current_offer;
                } else
                    System.out.println("valor entrat incorrecte. Ha de ser superior a 0 i inferior a " + current_player.getMoney() + ". Torna a provar:");
            }
        } catch (NumberFormatException e) {System.out.println("Format incorrecte. Torna-hi.");}
        return -1;
    }

    /**
     * @param current_player    Jugador que fa la compra
     * @param buy_player        Jugador que ven el terreny
     * @param buy_field         Terreny a comprar
     * @param initial_offer     Oferta inicial
     * @param output            sortida al fitxer
     * @return true si s'ha comprat el terreny, false altrament
     * @brief Gestiona la negociacio de la compra del terreny
     * @pre true
     * @post el boolean indicant si la compra s'ha dut a terme s'ha retornat
     */
    private boolean buyNegociation(Player current_player, Player buy_player, BoxField buy_field, int initial_offer, OutputManager output) {
        System.out.println("Comença la negociació");
        System.out.println(current_player.getName() + "> COMPRAR " + buy_field.getName() + " " + initial_offer);
        Player offer_active_player = buy_player;
        while (true) {
            System.out.println(offer_active_player.getName() + ": indiqui ok si accepta la oferta, no si la rebutja o un valor per fer una contraoferta:");
            String tmp;
            if (offer_active_player == current_player) tmp = current_player.stringValueSelection("buyBuyerOffer", offer_active_player, buy_field, initial_offer, 0);
            else tmp = buy_player.stringValueSelection("buySellerOffer", offer_active_player, buy_field, initial_offer, 0);
            if (tmp.equals("ok")) {
                output.fileWrite(offer_active_player.getName() + "> Accepta la oferta");
                return buy(current_player, buy_player, buy_field, initial_offer);
            } else if (tmp.equals("no")) {
                output.fileWrite(offer_active_player.getName() + "> No accepta la oferta");
                System.out.println("Les negociacions no han concluit i per tant la compra no es farà efectiva");
                return false;
            } else if (Integer.parseInt(tmp) < 0 || Integer.parseInt(tmp) >= current_player.getMoney()) {
                System.out.println("Valor incorrecte, la oferta ha de superior a 0 i inferior a " + current_player.getMoney());
            } else {
                initial_offer = Integer.parseInt(tmp);
                output.fileWrite("\t" + offer_active_player.getName() + "> Contraoferta: " + initial_offer + "€");
                if (offer_active_player == buy_player) offer_active_player = current_player;
                else offer_active_player = buy_player;
            }
        }
    }

    /**
     * @param current_player    Jugador que fa la compra
     * @param buy_player        Jugador que ven el terreny
     * @param buy_field         Terreny a comprar
     * @param final_offer       Oferta final
     * @return true si s'ha comprat el terreny, false altrament
     * @brief Gestiona la negociacio de la compra del terreny
     * @pre true
     * @post el boolean indicant si la compra s'ha dut a terme s'ha retornat
     */
    private boolean buy(Player current_player, Player buy_player, BoxField buy_field, int final_offer) {
        current_player.pay(final_offer);
        buy_player.charge(final_offer);
        current_player.addBox(buy_field);
        buy_player.removeBox(buy_field);
        System.out.println("La venda s'ha fet efectiva, per un preu de " + final_offer + "€");
        return true;
    }
}
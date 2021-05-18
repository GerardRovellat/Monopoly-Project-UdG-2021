import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Marc Got
 * @file Buy.java
 * @class Buy
 * @brief Implementa la interifíce d'optionalActions. Aquesta implementació permet a un jugador comprar una de les
 * propietats de un dels altres jugadors contraris. Aquesta compra és negociable
 */

public class Buy implements optionalActions{

    /**
     * @brief Constructor de Buy.
     * @pre \p true
     * @post Crea una acció opcional Buy.
     */
    public Buy() {};

    /**
     * @brief toString per mostrar la descripció de l'acció Buy per text.
     * @pre \p true
     * @post Mostra la descripció de Buy
     * @return String de la sortida per pantalla de Buy
     */
    @Override // Heretat d'Object
    public String toString() {
        return "Comprar: Fer una oferta de compra a un altre jugador";
    }

    /**
     * @brief Mètode per executar el procés de compra un terreny en propietat d'un jugador.
     * @pre Jugador \p current_player != null i Moviment \p m != null
     * @post Una propietat de algun jugador ha estat comprada o la acció s'ha cancelat
     * @param players ArrayList de jugadors que estan jugant al Monopoly.
     * @param current_player Jugador que fa la compra.
     * @param m Moviment que crida Buy, en aquesta implementació, \p m no és usada però s'ha de passar.
     * @return \p true si el procés s'ha realitzat, \p false altrament.
     */
    public boolean execute(ArrayList<Player> players,Player current_player,Movement m) {
        OutputManager output = m.getOutput();
        boolean is_possible = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("PROPIETATS DELS JUGADORS");
        for (Player aux : players) {
            System.out.println(aux.toString());
        }
        System.out.println("A QUIN JUGADOR L'HI VOL FER LA OFERTA?:");
        int cont = 0;
        for (Player aux : players) {
            if (aux.getName() != current_player.getName() && aux.getFields().size() > 0) {
                System.out.println(cont + ". " + aux.getName());
                cont++;
            }
        }
        if (cont != 0) {
            int value = -1;
            try {
                System.out.println("Selecioni la opcio que desitgi:");
                while (value < 0 || value > cont-1) {
                    //value = scan.nextInt();
                    value = current_player.optionSelection("buyPlayerSelect",current_player,null,null,players,null,0,null);
                    if (value < 0 || value > cont-1)
                        System.out.println("La opcio entrada es incorrecte ( ha de ser un enter entre 0 i " + (cont - 1) + " ). Torni a provar:");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Format incorrecte. Torna-hi.");
            }
            Player buy_player = players.get(value);
            ArrayList<Field> fields = buy_player.getFields();
            if (fields.size()!=0) {
                System.out.println("Selecioni la propietat de " + buy_player.getName() + " que desitja comprar:");
                cont = 0;
                for (Field aux : fields) {
                    System.out.println(cont + ". " + aux.getName() + " | " + aux.getPrice());
                    cont++;
                }
                try {
                    System.out.println("Selecioni la opcio que desitgi:");
                    value = -1;
                    while (value < 0 || value > cont - 1) {
                        //value = scan.nextInt();
                        value = current_player.optionSelection("buyFieldSelect",buy_player,null,null,null,null,0,null);
                        if (value < 0 || value > cont - 1)
                            System.out.println("La opcio entrada es incorrecte ( ha de ser un enter entre 0 i " + (cont - 1) + " ). Torni a provar:");
                    }
                }
                catch(NumberFormatException e){
                    System.out.println("Format incorrecte. Torna-hi.");
                }
                Field buy_field = fields.get(value);
                int current_offer = 0; // -1 if not
                try {
                    while (current_offer <= 0 || current_offer > current_player.getMoney()){
                        System.out.println("Indiqui la oferta inicial:");
                        current_offer = current_player.optionSelection("buyInitalOffer",current_player,buy_field,null,null,null,0,null);
                        if (current_offer <= 0 || current_offer > current_player.getMoney()) System.out.println("valor entrat incorrecte. Ha de ser superior a 0 i inferior a " + current_player.getMoney());
                    }
                }
                catch (NumberFormatException e){
                    System.out.println("Format incorrecte. Torna-hi.");
                }
                output.fileWrite(current_player.getName() + "> Vol vendre " + buy_field.getName() + " per " + current_offer + "€");
                System.out.println("Oferta realitzada.");
                System.out.println("Comença la negociació");
                boolean buy_final = false;
                System.out.println(current_player.getName() + "> COMPRAR " + buy_field.getName() + " " + current_offer);
                Player offer_active_player = buy_player;
                while (!buy_final) {
                    System.out.println(offer_active_player.getName() + ": indiqui ok si accepta la oferta, no si la rebutja o un valor per fer una contraoferta:");
                    //String tmp = scan.next();
                    String tmp = "";
                    if (offer_active_player == current_player) tmp = current_player.stringValueSelection("buyBuyerOffer",offer_active_player,buy_field,current_offer,0);
                    else tmp = buy_player.stringValueSelection("buySellerOffer",offer_active_player,buy_field,current_offer,0);
                    if (tmp.equals("ok")) {
                        buy_final = true;
                        output.fileWrite(offer_active_player.getName() + "> Accepta la oferta");
                    } else if (tmp.equals("no")) {
                        buy_final = true;
                        current_offer = -1;
                        output.fileWrite(offer_active_player.getName() + "> No accepta la oferta");
                    } else if (Integer.parseInt(tmp) < 0 || Integer.parseInt(tmp) >= current_player.getMoney()) {
                        System.out.println("Valor incorrecte, la oferta ha de superior a 0 i inferior a " + current_player.getMoney());
                    } else {
                        current_offer = Integer.parseInt(tmp);
                        output.fileWrite("\t" +  offer_active_player.getName() + "> Offereix " + current_offer + "€");
                        if (offer_active_player == buy_player) offer_active_player = current_player;
                        else offer_active_player = buy_player;
                    }
                }
                if (current_offer == -1) {
                    System.out.println("Les negociacions no han concluit i per tant la compra no es farà efectiva");
                    is_possible = false;
                }
                else {
                    current_player.pay(current_offer);
                    buy_player.charge(current_offer);
                    current_player.addBox(buy_field);
                    buy_player.removeBox(buy_field);
                    System.out.println("La venda s'ha fet efectiva, per un preu de " + current_offer + "€");
                }
            }
            else{
                System.out.println("Aquest jugador no te cap propietat en posesió per poder comprar");
                is_possible = false;
            }
        }
        else {
            System.out.println("No hi ha cap jugador amb propietats per comprar");
            is_possible = false;
        }
        return is_possible;
    }


}
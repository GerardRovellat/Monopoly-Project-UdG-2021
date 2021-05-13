import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Marc Got
 * @file Loan.java
 * @class Loan
 * @brief Classe que administra els préstecs entre Jugadors del Monopoly.
 */
public class Loan implements optionalActions{

    /**
     * @brief Constructor per defecte de Loan.
     * @pre \p true
     * @post Crea una acció opcional Loan.
     */
    public Loan() {};

    /**
     * @brief toString per mostrar la descripció de l'acció LuckCard per text.
     * @pre \p true
     * @post Mostra la descripció de LuckCard.
     * @return String de la sortida per pantalla de LuckCard.
     */
    @Override
    public String toString() {
        return "PRÉSTEC: pot demanar una quantitat prestada a un altre jugador";
    }

    /**
     * @brief Mètode per executar el procés de demanar un préstec a un Jugador.
     * @pre \p current_player != null
     * @post S'ha demanat un préstec a un Jugador.
     * @return \p true quan ha acabat de demanar el prestéc.
     */
    public boolean execute(ArrayList<Player> players,Player current_player, Movement aux) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Sel·lecioni el jugador a qui l'hi vol demanar el prestec:");
        int cont = 0;
        for (Player player : players) {
            if (player != current_player) {
                System.out.println(cont + ". " + player.getName());
            }
            cont++;
        }
        int value = -1;
        while(value < 0 || value > cont) {
            value = scan.nextInt();
            if (value < 0 || value > cont || value == players.indexOf(current_player)) System.out.println("El valor entrat no es correcte. Torn-hi a provar:");
        }
        Player loan_player = players.get(value);
        int current_offer = -1;
        while (current_offer < 0 || current_offer > loan_player.getMoney()) {
            System.out.println("Valor de la oferta:");
            current_offer = scan.nextInt();
            if (current_offer < 0 || current_offer > loan_player.getMoney()) System.out.println("Valor incorrecte. El valor ha de ser superior o igual a 0 i inferior a " + loan_player.getMoney());
        }

        System.out.println("Info del prestes:");
        System.out.println(current_player.getName() + "> PRESTEC " + loan_player.getName() + " " + current_offer);
        Player offer_active_player = loan_player;
        String interests_string = "";
        int interests = -1;
        int turns = 0;
        boolean negociate = false;

        while (!negociate) {
            if (turns==0)System.out.println(offer_active_player.getName() + ": indiqui no si rebutja la oferta, o el interes que demana tot seguit de el numero de torns (ex: 15 3).");
            else System.out.println((offer_active_player.getName() + ": indiqui no si rebutja la oferta, ok si la accepta o el interes que demana tot seguit de el numero de torns (ex: 15 3) si vol fer una contraoferta"));
            interests_string = scan.next();
            if (interests_string.equals("no")) {
                turns = 0;
                negociate = true;
            }
            else if (interests_string.equals("ok")) {
                negociate = true;
            }
            else {
                interests = Integer.parseInt(interests_string);
                turns = scan.nextInt();
            }
            if (offer_active_player == current_player) offer_active_player = loan_player;
            else offer_active_player = current_player;
        }

        if (turns > 0) {
            current_player.addLoan(loan_player,current_offer,interests,turns);
            loan_player.pay(current_offer);
            current_player.charge(current_offer);
            System.out.println("El prestec s'ha dut a terme");
        }
        else {
            System.out.println("La operacio s'ha cancelat");
        }
        return true;
    }
}
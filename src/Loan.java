import java.util.ArrayList;
import java.util.Scanner;

public class Loan implements optionalActions{

    public Loan() {};

    @Override // Heretat d'Object
    public String toString() {

        return "PRÉSTEC: pot demanar una quantitat prestada a un altre jugador";
    }

    public boolean execute(ArrayList<Player> players,Player actual_player, Movement aux) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Selecioni el jugador a qui l'hi vol demanar el prestec:");
        int cont = 0;
        for (Player player : players) {
            System.out.println(cont + ". " + player.getName());
            cont++;
        }
        int value = -1;
        while(value < 0 || value > cont) {
            value = scan.nextInt();
            if (value < 0 || value > cont) System.out.println("El valor entrat no es correcte. Torn-hi a provar:");
        }
        Player loan_player = players.get(value);
        int actual_offer = -1;
        while (actual_offer < 0) {
            System.out.println("Valor de la oferta:");
            actual_offer = scan.nextInt();
            if (actual_offer < 0) System.out.println("Valor incorrecte. El valor ha de ser superior o igual a 0");
        }

        System.out.println("Info del prestes:");
        System.out.println(actual_player.getName() + "> PRESTEC " + loan_player.getName() + " " + value);
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
            if (offer_active_player == actual_player) offer_active_player = loan_player;
            else offer_active_player = actual_player;
        }

        if (turns > 0) {
            actual_player.addLoan(loan_player,value,interests,turns);
            System.out.println("El prestec s'ha dut a terme");
        }
        else {
            System.out.println("La operacio s'ha cancelat");
        }






        return false;
    }
}
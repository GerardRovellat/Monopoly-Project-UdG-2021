import java.util.ArrayList;

/**
 * @author Marc Got
 * @file Loan.java
 * @class Loan
 * @brief Classe que administra els préstecs entre Jugadors del Monopoly.
 */
public class OpActLoan implements optionalActions{

    /**
     * @brief Constructor per defecte de Loan.
     * @pre \p true
     * @post Crea una acció opcional Loan.
     */
    public OpActLoan() {}

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
        System.out.println("Sel·lecioni el jugador a qui l'hi vol demanar el prestec:");
        Player loan_player = playerSelect(players,current_player);
        int current_offer = offer(current_player, loan_player);
        return negociation(current_player,loan_player,current_offer);

    }

    // Pre: minim dos jugadors i un dells != current player
    private Player playerSelect(ArrayList<Player> players, Player current_player) {
        for (Player player : players) {
            if (player != current_player && player.getMoney() > 0) System.out.println(players.indexOf(player) + ". " + player.getName());
        }
        while(true) {
            int value = current_player.optionSelection("loanPlayerSelect",null,null,null,players,null,0,null);
            if (value >= 0 && value < players.size() && value != players.indexOf(current_player)) return players.get(value);
            else System.out.println("El valor entrat no es correcte. Torn-hi a provar:");
        }
    }

    private int offer(Player current_player, Player loan_player) {
        int current_offer = -1;
        while (current_offer < 0 || current_offer > loan_player.getMoney()) {
            System.out.println("Valor de la oferta:");
            current_offer = loan_player.optionSelection("loanInitialOffer",null,null,null,null,null,0,null);
            if (current_offer < 0 || current_offer > loan_player.getMoney()) System.out.println("Valor incorrecte. El valor ha de ser superior o igual a 0 i inferior a " + loan_player.getMoney());
        }
        System.out.println("Info del prestes:");
        System.out.println(current_player.getName() + "> PRESTEC " + loan_player.getName() + " " + current_offer);
        return current_offer;
    }

    private boolean negociation(Player current_player, Player loan_player, int current_offer) {
        Player offer_active_player = loan_player;
        String interests_string;
        int interests = -1;
        int turns = 0;
        while (true) {
            if (turns==0)System.out.println(offer_active_player.getName() + ": indiqui no si rebutja la oferta, o el interes que demana tot seguit de el numero de torns (ex: 15 3).");
            else System.out.println((offer_active_player.getName() + ": indiqui no si rebutja la oferta, ok si la accepta o el interes que demana tot seguit de el numero de torns (ex: 15 3) si vol fer una contraoferta"));
            interests_string = offer_active_player.stringValueSelection("loanInterestOffer",loan_player,null,current_offer,interests);
            if (interests_string.equals("no")) {
                System.out.println("La operacio s'ha cancelat");
                return false;
            }
            else if (interests_string.equals("ok")) {
                giveLoan(loan_player,current_player,current_offer,interests,turns);
                return true;
            }
            else {
                interests = Integer.parseInt(interests_string);
                turns = Integer.parseInt(offer_active_player.stringValueSelection("loanTurnsOffer",null,null,0,0));
            }
            if (offer_active_player == current_player) offer_active_player = loan_player;
            else offer_active_player = current_player;
        }
    }

    private boolean giveLoan (Player loan_player, Player current_player, int current_offer, int interests, int turns) {
        current_player.addLoan(loan_player,current_offer,interests,turns);
        loan_player.pay(current_offer);
        current_player.charge(current_offer);
        System.out.println("El prestec s'ha dut a terme");
        return true;
    }
}
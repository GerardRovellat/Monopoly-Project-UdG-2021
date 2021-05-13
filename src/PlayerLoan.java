/**
 * @author Marc Got
 * @file Movement.java
 * @class Movement
 * @brief Classe que s'encarrega de gestionar les accions que es poden fer en un moviment. Aquest moviment sera fet
 * sobre un Jugador.
 */
public class PlayerLoan {
    Player loner;
    Player loaned;
    int value;
    int interest;
    int turns;

    public PlayerLoan(Player loner,Player loaned,int value,int interest,int turns) {
        this.loner = loner;
        this.loaned = loaned;
        this.value = value;
        this.interest = interest;
        this.turns = turns;
    }

    public void nextTurn() {
        turns=turns-1;
    }

    public int returnValue() {
        Double result = value * ( 1 + (double) interest / 100 );
        return result.intValue();
    }

    public boolean isEnd() {
        return turns == 0;
    }

    public boolean payLoan() {
        boolean aux = false;
        if (isEnd()) {
            System.out.println("El prestec ha acabat. INFO DEL PRESTEC:");
            System.out.println(this.toString());
            loaned.pay(returnValue());
            loner.charge(returnValue());
            aux = true;
        }
        return aux;
    }

    public String smallPrint() {
        return value + "€ en " + turns + " torns a " + loner.getName() + " i un interes del " + interest + "%";
    }

    @Override
    public String toString() {
        System.out.println("Propietari: " + loner.getName());
        System.out.println("Valor: " + value);
        System.out.println("Interes: " + interest);
        System.out.println("Torns: " + turns);
        System.out.println("A retornar: " + returnValue());
        return "";
    }

}

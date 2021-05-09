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
        turns=turns--;
    }

    public int returnValue() {
        return (int) (value * (interest/100));
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

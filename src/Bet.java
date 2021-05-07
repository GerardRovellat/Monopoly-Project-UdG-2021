import java.lang.Math;

public class Bet extends Box{

    private int[] combinations = new int[]{0,1,2,3,4,5,6,5,4,3,2,1};

    public Bet(int position, String type) {
        super(position, type, "APOSTA");
    }

    public int betResult(int quanity, int bet, int dice_result) {
        if (dice_result >= bet) {
            Double aux = bet * (1 + Math.pow(10,((36 - combinations[bet-1]) / 36)));
            return aux.intValue();
        }
        else return -1;
    }
}

import javafx.util.Pair;

import java.lang.Math;
import java.util.ArrayList;

public class Bet extends Box{
    private double[] combinations = new double[]{0,0,0,1,3,6,10,15,21,26,30,33,35};

    public Bet(int position, String type) {
        super(position, type, "APOSTA");

    }

    public int betResult(int quanity, int bet, int dice_result) {
        if (dice_result >= bet) {
            Double aux = quanity * (1 + 10 * combinations[bet] / 36 ) ;
            return aux.intValue();
        }
        else return -1;
    }
}
